// package com.management.yuvro.config;

// import com.management.yuvro.jpa.entity.*;
// import com.management.yuvro.jpa.repository.*;
// import com.management.yuvro.service.QuestionService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.time.LocalDateTime;

// @Component
// public class TestDataLoader implements CommandLineRunner {
//     @Autowired
//     TopicRepository topicRepository;
//     @Autowired
//     BatchRepository batchRepository;

//     @Autowired
//     CandidateRepository candidateRepository;

//     @Autowired
//     MCQQuestionRepository mcqQuestionRepository;

//     @Autowired
//     MCQOptionRepository optionRepository;

//     @Autowired
//     QuestionService questionService;

//     @Autowired
//     AssessmentRepository assessmentRepository;

//     @Autowired
//     CandidateAssessmentRepository candidateAssessmentRepository;

//     @Autowired
//     AttemptedQuestionRepository attemptedQuestionRepository;

//     @Override
//     public void run(String... args) throws Exception {

//         Batch batch = saveBatch();
//         Topic topic = saveTopic();
//         Candidate candidate = saveCandidate();saveCandidate();saveCandidate();
//         MCQQuestion mcqQuestion = saveQuestion(topic);
//         saveQuestion(topic);


//         Assessment assessment = saveAssessment(batch);

// //        var questions = questionService.getQuestionsByTopicId(1l);
// //        System.out.println(questions);

//     }

//     private Batch saveBatch() {
//         var batch = new Batch();
//         batch.setBatchName("test batch");
//         batch.setBatchTitle("test batch title");
//         batch.setDescription("test description");
//         return batchRepository.save(batch);
//     }

//     private Assessment saveAssessment(Batch batch) {
//         Assessment assessment = new Assessment();
//         assessment.setAssessmentName("test assessment");
//         assessment.setAssessmentType("MCQ");
//         assessment.setDescription("test description");
//         assessment.setSubject("test subject");
//         assessment.setTopic("test topic");
//         assessment.setCreatedDateTime(LocalDateTime.now());
//         assessment.setStartDateTime(LocalDateTime.now());
//         assessment.setDueDateTime(LocalDateTime.now().plusDays(1));
//         assessment.setDurationInHours("1");
//         assessment.setBatch(batch);

//         return assessmentRepository.save(assessment);
//     }

//     private MCQQuestion saveQuestion(Topic topic) {
//         MCQQuestion question = new MCQQuestion();
//         question.setQuestion("test question");
//         question.setQuestionType("MCQ");
//         question.setAnswer("op1");
//         question.setScore(2);
//         question.setTopic(topic);
//         question =
//                 mcqQuestionRepository.save(question);

//         MCQOptions options = saveOptions(question);

//         return question;
//     }

//     private MCQOptions saveOptions(MCQQuestion question) {
//         MCQOptions options = new MCQOptions();
//         options.setOption("op1");
//         options.setQuestion(question);
//         optionRepository.save(options);

//         options = new MCQOptions();
//         options.setOption("op2");
//         options.setQuestion(question);
//         optionRepository.save(options);

//         options = new MCQOptions();
//         options.setOption("op3");
//         options.setQuestion(question);
//         optionRepository.save(options);

//         options = new MCQOptions();
//         options.setOption("op4");
//         options.setQuestion(question);
//         optionRepository.save(options);

//         return options;
//     }

//     private Candidate saveCandidate() {
//         Candidate candidate = new Candidate();
//         candidate.setName("Jon Snow");
//         return candidateRepository.save(candidate);
//     }

//     private Topic saveTopic() {
//         var topic = new Topic();
//         topic.setTopicName("test topic");
//         topic.setTopicLocked(true);
//         return topicRepository.save(topic);
//     }
// }
