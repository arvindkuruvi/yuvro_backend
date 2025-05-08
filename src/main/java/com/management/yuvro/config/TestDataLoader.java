package com.management.yuvro.config;

import com.management.yuvro.jpa.entity.*;
import com.management.yuvro.jpa.repository.CandidateRepository;
import com.management.yuvro.jpa.repository.MCQOptionRepository;
import com.management.yuvro.jpa.repository.MCQQuestionRepository;
import com.management.yuvro.jpa.repository.TopicRepository;
import com.management.yuvro.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDataLoader implements CommandLineRunner {
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    MCQQuestionRepository mcqQuestionRepository;

    @Autowired
    MCQOptionRepository optionRepository;

    @Autowired
    QuestionService questionService;

    @Override
    public void run(String... args) throws Exception {

        Topic topic = saveTopic();
        Candidate candidate = saveCandidate();
        MCQQuestion mcqQuestion = saveQuestion(topic);


//        var questions = questionService.getQuestionsByTopicId(1l);
//        System.out.println(questions);

    }

    private MCQQuestion saveQuestion(Topic topic) {
        MCQQuestion question = new MCQQuestion();
        question.setQuestion("test question");
        question.setTopic(topic);
        question =
                mcqQuestionRepository.save(question);

        MCQOptions options = saveOptions(question);

        return question;
    }

    private MCQOptions saveOptions(MCQQuestion question) {
        MCQOptions options = new MCQOptions();
        options.setOption("op1");
        options.setQuestion(question);
        optionRepository.save(options);
        return options;
    }

    private Candidate saveCandidate() {
        Candidate candidate = new Candidate();
        candidate.setName("Jon Snow");
        return candidateRepository.save(candidate);
    }

    private Topic saveTopic() {
        var topic = new Topic();
        topic.setTopicName("test topic");
        topic.setTopicLocked(true);
        return topicRepository.save(topic);
    }
}
