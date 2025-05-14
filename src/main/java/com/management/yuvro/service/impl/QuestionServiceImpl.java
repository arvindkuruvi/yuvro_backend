package com.management.yuvro.service.impl;

import com.management.yuvro.dto.PageDTO;
import com.management.yuvro.dto.QuestionsDTO;
import com.management.yuvro.dto.request.MCQOptionRequest;
import com.management.yuvro.dto.request.SaveQuestionRequest;
import com.management.yuvro.dto.request.ValidateQuestionRequest;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.dto.response.GetQuestionsResponse;
import com.management.yuvro.dto.response.SaveQuestionResponse;
import com.management.yuvro.enums.QuestionType;
import com.management.yuvro.exceptions.InvalidOptionException;
import com.management.yuvro.jpa.entity.MCQOptions;
import com.management.yuvro.jpa.entity.MCQQuestion;
import com.management.yuvro.jpa.entity.Topic;
import com.management.yuvro.jpa.repository.MCQOptionRepository;
import com.management.yuvro.jpa.repository.MCQQuestionRepository;
import com.management.yuvro.jpa.repository.TopicRepository;
import com.management.yuvro.mapper.QuestionMapper;
import com.management.yuvro.service.QuestionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.management.yuvro.constants.Constants.*;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private MCQQuestionRepository questionRepository;
    private TopicRepository topicRepository;
    private MCQOptionRepository mcqOptionRepository;

    @Autowired
    QuestionMapper questionMapper;

    public QuestionServiceImpl(MCQQuestionRepository questionRepository, TopicRepository topicRepository,
                               MCQOptionRepository mcqOptionRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
        this.mcqOptionRepository = mcqOptionRepository;
    }

    @Transactional
    @Override
    public CommonApiResponse saveQuestion(SaveQuestionRequest questionRequest) {

        log.info("Adding questions");

        Topic topic = validateAndGetTopic(questionRequest.getTopicId());

        var questType = QuestionType.fromQuestionType(questionRequest.getQuestionType()).toString();

        var question = new MCQQuestion();
        question.setQuestionType(questType);
        question.setQuestion(questionRequest.getQuestion());
        question.setAnswer(questionRequest.getAnswer());
        question.setTopic(topic);

        question = questionRepository.save(question);

        saveMCQOptions(question, questionRequest.getOptions());

        var questionResponse = new SaveQuestionResponse(String.valueOf(question.getQuestionId()));
        questionResponse.setMessage(QUESTION_SAVE_SUCCESS);
        questionResponse.setSuccess(true);

        log.info(QUESTION_SAVE_SUCCESS);

        return questionResponse;
    }

    private List<MCQOptions> saveMCQOptions(MCQQuestion question, Set<MCQOptionRequest> options) {

        log.info("Saving options for question :: {}", question.getQuestionId());

        List<MCQOptions> mcqOptions = new ArrayList<MCQOptions>();

        for (MCQOptionRequest option : options) {
            mcqOptions.add(createOption(option, question));
        }

        return mcqOptionRepository.saveAll(mcqOptions);
    }

    private MCQOptions createOption(MCQOptionRequest optionRequest, MCQQuestion question) {
        return MCQOptions.builder().option(optionRequest.getOption()).question(question).build();
    }

    private Topic validateAndGetTopic(Long subTopicId) {
        return topicRepository.findById(subTopicId)
                .orElseThrow(() -> new ResourceNotFoundException("Subtopic with id " + subTopicId + " is not found"));
    }

    @Transactional
    @Override
    public GetQuestionsResponse getQuestionsByTopicId(Long topicId) {

        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("Topic with id " + topicId + " is not found");
        }

        List<MCQQuestion> questions = questionRepository.getByTopic(topicId);

        var getQuestions = new GetQuestionsResponse(questions.parallelStream().map(this::mapQuestion).toList());
        getQuestions.setMessage("Fetched successfully");
        getQuestions.setSuccess(true);

        return getQuestions;
    }

    private QuestionsDTO mapQuestion(MCQQuestion mcqQuestion) {

        QuestionsDTO questionsDTO = new QuestionsDTO();
        BeanUtils.copyProperties(mcqQuestion, questionsDTO);

        questionsDTO.setOptions(mcqQuestion.getMcqOptions().stream().map(MCQOptions::getOption).toList());

        return questionsDTO;
    }

    @Override
    public CommonApiResponse validateQuestion(ValidateQuestionRequest validateQuestionRequest) {
        var question = questionRepository.findById(validateQuestionRequest.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Question with id " + validateQuestionRequest.getQuestionId() + " is not found"));

        var response = new CommonApiResponse();

        if (validateQuestionRequest.getSelectedAnswer().equals(question.getAnswer())) {
            response.setMessage(OPTION_CORRECT_MSG);
            response.setSuccess(true);
        } else
            throw new InvalidOptionException(OPTION_INCORRECT_MSG);

        return response;
    }

    @Override
    public PageDTO<QuestionsDTO> getAllQuestions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("questionId"));
        return questionMapper.convertPageOfMCQQuestionsToPageDTOOfQuestionsDTO(questionRepository.findAll(pageable));
    }

}
