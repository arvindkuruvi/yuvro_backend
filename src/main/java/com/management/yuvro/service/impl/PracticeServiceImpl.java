package com.management.yuvro.service.impl;

import com.management.yuvro.dto.QuestionsAttenedDTO;
import com.management.yuvro.dto.request.AttemptPracticeRequest;
import com.management.yuvro.dto.request.SavePracticeQuestionRequest;
import com.management.yuvro.dto.response.AttemptPracticeResponse;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.GetPracticeQuestionResponse;
import com.management.yuvro.dto.response.PracticeResultResponse;
import com.management.yuvro.exceptions.EntityNotFoundException;
import com.management.yuvro.jpa.entity.MCQOptions;
import com.management.yuvro.jpa.entity.MCQQuestion;
import com.management.yuvro.jpa.entity.Practice;
import com.management.yuvro.jpa.entity.PracticeQuestion;
import com.management.yuvro.jpa.repository.CandidateRepository;
import com.management.yuvro.jpa.repository.MCQQuestionRepository;
import com.management.yuvro.jpa.repository.PracticeQuestionRepository;
import com.management.yuvro.jpa.repository.PracticeRepository;
import com.management.yuvro.service.PracticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.management.yuvro.constants.Constants.*;

@Service
@Slf4j
public class PracticeServiceImpl implements PracticeService {
    private PracticeRepository practiceRepository;
    private MCQQuestionRepository questionRepository;

    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    PracticeQuestionRepository practiceQuestionRepository;


    public PracticeServiceImpl(PracticeRepository practiceRepository, MCQQuestionRepository mcqQuestionRepository) {
        this.practiceRepository = practiceRepository;
        this.questionRepository = mcqQuestionRepository;
    }

    @Override
    public GetPracticeQuestionResponse getPracticeIfExists(Long candidateId, Long topicId) {
        var practiceOptional = practiceRepository.findByTopicIdAndCandidate_CandidateId(topicId, candidateId);
        List<QuestionsAttenedDTO> questionsAttenedDTOS = null;
        String msg = null;

        if (practiceOptional.isEmpty()) {

            msg = String.format(PRACTICE_NOT_ATTEMPTED_MSG, topicId);

            questionsAttenedDTOS = getQuestionByTopicAndMap(topicId, null);

        } else {

            msg = String.format(PRACTICE_ATTEMPTED_MSG, topicId);

            var practice = practiceOptional.get();

            questionsAttenedDTOS = getPracticeQuestionsAttended(practice, topicId);
        }

        log.info(msg);

        var getPracticeQuestionResponse = new GetPracticeQuestionResponse(candidateId, topicId, questionsAttenedDTOS);
        getPracticeQuestionResponse.setSuccess(true);
        getPracticeQuestionResponse.setMessage(msg);

        return getPracticeQuestionResponse;
    }

    private List<QuestionsAttenedDTO> getPracticeQuestionsAttended(Practice practice, Long topicId) {

        Map<Long, PracticeQuestion> practiceQuestionMap =
                practice.getPracticeQuestions().parallelStream()
                        .collect(Collectors.toMap(PracticeQuestion::getQuestionId, Function.identity()));

        return getQuestionByTopicAndMap(topicId, practiceQuestionMap);

    }

    private List<QuestionsAttenedDTO> getQuestionByTopicAndMap(Long topicId, Map<Long, PracticeQuestion> practiceQuestionMap) {
        List<MCQQuestion> questions = questionRepository.getByTopic(topicId);

        List<QuestionsAttenedDTO> questionsAttenedDTOS = new ArrayList<>();

        for (MCQQuestion question : questions) {
            var questionsAttenedDTO = new QuestionsAttenedDTO();

            var questionId = question.getQuestionId();

            BeanUtils.copyProperties(question, questionsAttenedDTO);

            questionsAttenedDTO.setOptions(question.getMcqOptions().parallelStream().map(MCQOptions::getOption).toList());

            if (!ObjectUtils.isEmpty(practiceQuestionMap)) {
                if (practiceQuestionMap.containsKey(questionId)) {
                    log.info("Question with id : {} attended by candidate", questionId);

                    var practiceQuestion = practiceQuestionMap.get(questionId);
                    questionsAttenedDTO.setSelectedAnswer(practiceQuestion.getSelectedAns());
                    questionsAttenedDTO.setCorrect(practiceQuestion.isCorrect());
                    questionsAttenedDTO.setQuestionAttended(true);
                }
            }

            questionsAttenedDTOS.add(questionsAttenedDTO);
        }
        return questionsAttenedDTOS;
    }


    @Override
    public CommonApiResponse savePracticeQuestion(SavePracticeQuestionRequest request) {


        var practice = getPracticeByPracticeId(request.getPracticeId());

        var practiceQuestion = new PracticeQuestion();

        practiceQuestion.setQuestionId(request.getQuestionId());
        practiceQuestion.setCorrect(request.isCorrect());
        practiceQuestion.setPractice(practice);
        practiceQuestion.setSelectedAns(request.getSelectedAns());

        practiceQuestionRepository.save(practiceQuestion);

        var response = new CommonApiResponse();
        response.setMessage(SAVE_SUCCESS);
        response.setSuccess(true);
        return response;

    }

    public Practice getPracticeByPracticeId(Long practiceId) {
        return practiceRepository.findById(practiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Practice not found with id :: " + practiceId));
    }

    @Override
    public PracticeResultResponse getPracticeResult(Long practiceId) {

        var practice = getPracticeByPracticeId(practiceId);

        var response = new PracticeResultResponse();
        response.setSuccess(true);
        response.setTotalQuestions(questionRepository.countByTopicId(practice.getTopicId()));
        response.setTotalCorrectAnswers(practice.getPracticeQuestions().stream().map(PracticeQuestion::isCorrect).count());
        return response;
    }

    @Override
    public CommonApiResponse attemptPractice(AttemptPracticeRequest request) {
        var candidateOptional = candidateRepository.findById(request.getCandidateId());

        if (candidateOptional.isEmpty())
            throw new EntityNotFoundException("Candidate not found with id :: " + request.getCandidateId());

        var practiceOptional = practiceRepository.findByTopicIdAndCandidate_CandidateId(request.getTopicId(), request.getCandidateId());

        var response = new AttemptPracticeResponse();
        response.setSuccess(true);
        response.setMessage("Candidate attempted");

        if (practiceOptional.isEmpty()) {
            var practice = new Practice();
            practice.setCandidate(candidateOptional.get());
            practice.setTopicId(request.getTopicId());
            practice = practiceRepository.save(practice);

            response.setPracticeId(practice.getPracticeId());
        } else {
            response.setPracticeId(practiceOptional.get().getPracticeId());
        }

        return response;
    }

}
