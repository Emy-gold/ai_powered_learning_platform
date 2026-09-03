package org.backend.modules.studentAnswer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.assessment.Answer;
import org.backend.domains.assessment.Question;
import org.backend.domains.assessment.StudentAnswer;
import org.backend.modules.answer.repository.AnswerRepository;
import org.backend.modules.question.repository.QuestionRepository;
import org.backend.modules.studentAnswer.dto.StudentAnswerRequest;
import org.backend.modules.studentAnswer.dto.StudentAnswerResponse;
import org.backend.modules.studentAnswer.mapper.StudentAnswerMapper;
import org.backend.modules.studentAnswer.repository.StudentAnswerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;
    private final StudentAnswerMapper studentAnswerMapper;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    //--------------------------------Create student answer------------------------------------------------------

    @Transactional
    public StudentAnswerResponse create(StudentAnswerRequest request) {

        StudentAnswer studentAnswer = studentAnswerMapper.toEntity(request);

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Answer answer = answerRepository.findById(request.getAnswerId())
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        studentAnswer.setQuestion(question);
        studentAnswer.setAnswer(answer);
        studentAnswer.setCorrect(answer.isCorrect());
        studentAnswer.setSubmittedAt(LocalDateTime.now());

        return studentAnswerMapper.toResponse(
                studentAnswerRepository.save(studentAnswer)
        );
    }


    //--------------------------------Get student answer by id------------------------------------------------

    public StudentAnswerResponse getById(Long id) {

        StudentAnswer studentAnswer = studentAnswerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student answer not found"));

        return studentAnswerMapper.toResponse(studentAnswer);
    }


    //--------------------------------Get all student answers--------------------------------------------------

    public List<StudentAnswerResponse> getAll() {

        List<StudentAnswerResponse> studentAnswers = new ArrayList<>();

        for (StudentAnswer studentAnswer : studentAnswerRepository.findAll()) {
            studentAnswers.add(studentAnswerMapper.toResponse(studentAnswer));
        }

        return studentAnswers;
    }


    //--------------------------------Update student answer----------------------------------------------------

    @Transactional
    public StudentAnswerResponse update(Long id, StudentAnswerRequest request) {

        StudentAnswer studentAnswer = studentAnswerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student answer does not exist"));

        Answer answer = answerRepository.findById(request.getAnswerId())
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        studentAnswer.setAnswer(answer);
        studentAnswer.setCorrect(answer.isCorrect());

        StudentAnswer saved = studentAnswerRepository.save(studentAnswer);

        return studentAnswerMapper.toResponse(saved);
    }


    //--------------------------------Delete student answer----------------------------------------------------

    @Transactional
    public void delete(Long id) {

        if (!studentAnswerRepository.existsById(id)) {
            throw new RuntimeException("Student answer does not exist");
        }

        studentAnswerRepository.deleteById(id);
    }
}