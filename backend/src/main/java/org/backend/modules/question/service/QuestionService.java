package org.backend.modules.question.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.assessment.Question;
import org.backend.domains.assessment.Quiz;
import org.backend.modules.question.dto.QuestionRequest;
import org.backend.modules.question.dto.QuestionResponse;
import org.backend.modules.question.mapper.QuestionMapper;
import org.backend.modules.question.repository.QuestionRepository;
import org.backend.modules.quiz.repository.QuizRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    //----------------------------------------Create the question function----------------------------------------------
    @Transactional
    public QuestionResponse create(QuestionRequest request){

        Question question = questionMapper.toEntity(request);
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        question.setQuiz(quiz);
        return questionMapper.toResponse(questionRepository.save(question));
    }

    //-----------------------------------------Get question by id-------------------------------------------------------
    public QuestionResponse getById(Long id){
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The question not found"));

        return questionMapper.toResponse(question);
    }

    //-----------------------------------------Get all the quizzes------------------------------------------------------
    public List<QuestionResponse> getAll(){
        List<QuestionResponse> questions = new ArrayList<>();
        for(Question question : questionRepository.findAll()){
            questions.add(questionMapper.toResponse(question));
        }

        return questions;
    }

    //-----------------------------------------Update the quiz----------------------------------------------------------
    @Transactional
    public QuestionResponse update(Long id, QuestionRequest request, Long userId, Authentication authentication)
        throws AccessDeniedException{

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The question does nto exists"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if(!isAdmin && !question.getCreatedBy().equals(userId)){
            throw  new AccessDeniedException("You do not have access to this question");
        }

        question.setQuestionText(request.getQuestionText());
        question.setPoints(request.getPoints());
        question.setOrderNumber(request.getOrderNumber());
        question.setQuestionType(request.getQuestionType());

        Question saved = questionRepository.save(question);
        return questionMapper.toResponse(saved);
    }

    //-----------------------------------------Delete the question------------------------------------------------------
    @Transactional
    public void delete(Long id,Long userId, Authentication authentication) throws AccessDeniedException{
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question does not exist"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if(!isAdmin && !question.getCreatedBy().equals(userId)){
            throw new AccessDeniedException("You do not own this question");
        }

        questionRepository.deleteById(id);
    }
}
