package org.backend.modules.studentAnswer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.assessment.Answer;
import org.backend.domains.assessment.Question;
import org.backend.domains.assessment.StudentAnswer;
import org.backend.domains.profile.StudentProfile;
import org.backend.modules.answer.repository.AnswerRepository;
import org.backend.modules.question.repository.QuestionRepository;
import org.backend.modules.student.repository.StudentRepository;
import org.backend.modules.studentAnswer.dto.StudentAnswerRequest;
import org.backend.modules.studentAnswer.dto.StudentAnswerResponse;
import org.backend.modules.studentAnswer.mapper.StudentAnswerMapper;
import org.backend.modules.studentAnswer.repository.StudentAnswerRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;
    private final StudentRepository studentProfileRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final StudentAnswerMapper studentAnswerMapper;

    @Transactional
    public StudentAnswerResponse create(
            StudentAnswerRequest request,
            Long userId
    ) {

        StudentProfile student = studentProfileRepository
                .findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found")
                );

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question is not found"));

        Answer answer = answerRepository.findById(request.getAnswerId())
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        if (!answer.getQuestion().getId().equals(question.getId())) {
            throw new RuntimeException(
                    "The answer does not belong to this question"
            );
        }

        StudentAnswer studentAnswer = studentAnswerRepository.findByStudentIdAndQuestionId(student.getId(), question.getId()
                        ).orElse(null);

        if (studentAnswer == null) {
            studentAnswer = new StudentAnswer();

            studentAnswer.setStudent(student);
            studentAnswer.setQuestion(question);
        }

        studentAnswer.setAnswer(answer);

        // Correctness is determined by the backend
        studentAnswer.setCorrect(answer.isCorrect());

        StudentAnswer saved = studentAnswerRepository.save(studentAnswer);

        return studentAnswerMapper.toResponse(saved);
    }

    public StudentAnswerResponse getById(Long id, Long userId
    ) throws AccessDeniedException {

        StudentAnswer studentAnswer = studentAnswerRepository.findById(id).
                        orElseThrow(() -> new RuntimeException("Student answer not found"));

        checkOwnership(studentAnswer, userId);

        return studentAnswerMapper.toResponse(studentAnswer);
    }

    public List<StudentAnswerResponse> getMyAnswers(Long userId) {

        StudentProfile student =
                studentProfileRepository.findByUserId(userId)
                        .orElseThrow(() -> new RuntimeException("Student profile not found"));

        return studentAnswerRepository
                .findByStudentId(student.getId())
                .stream()
                .map(studentAnswerMapper::toResponse)
                .toList();
    }

    @Transactional
    public StudentAnswerResponse update(Long id, StudentAnswerRequest request, Long userId
    ) throws AccessDeniedException {

        StudentAnswer studentAnswer =
                studentAnswerRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Student answer not found"));

        checkOwnership(studentAnswer, userId);

        Question question =
                questionRepository.findById(request.getQuestionId()).orElseThrow(() -> new RuntimeException("Question not found"));

        Answer answer = answerRepository.findById(request.getAnswerId())
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        if (!answer.getQuestion().getId().equals(question.getId())) {
            throw new RuntimeException(
                    "The answer does not belong to this question");
        }

        studentAnswer.setQuestion(question);
        studentAnswer.setAnswer(answer);
        studentAnswer.setCorrect(answer.isCorrect());

        StudentAnswer updated = studentAnswerRepository.save(studentAnswer);

        return studentAnswerMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id, Long userId
    ) throws AccessDeniedException {
        StudentAnswer studentAnswer = studentAnswerRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Student answer not found"));

        checkOwnership(studentAnswer, userId);

        studentAnswerRepository.delete(studentAnswer);
    }

    private void checkOwnership(StudentAnswer studentAnswer, Long userId
    ) throws AccessDeniedException {

        if (!studentAnswer.getStudent()
                .getUser()
                .getId()
                .equals(userId)) {

            throw new AccessDeniedException("You are not allowed to access this answer");
        }
    }
}