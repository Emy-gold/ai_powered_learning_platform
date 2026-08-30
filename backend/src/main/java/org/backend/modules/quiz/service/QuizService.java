package org.backend.modules.quiz.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.assessment.Quiz;
import org.backend.domains.learning.Lesson;
import org.backend.modules.lesson.repository.LessonRepository;
import org.backend.modules.quiz.dto.QuizRequest;
import org.backend.modules.quiz.dto.QuizResponse;
import org.backend.modules.quiz.mapper.QuizMapper;
import org.backend.modules.quiz.repository.QuizRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private  final LessonRepository lessonRepository;


    //-----------------------------------Create the lesson function------------------------------------------------------
    @Transactional
    public QuizResponse create(QuizRequest request){

        Quiz quiz = quizMapper.toEntity(request);
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        quiz.setLesson(lesson);
        return quizMapper.toResponse(quizRepository.save(quiz));
    }

    //-------------------------------Get quiz by id--------------------------------------------------------
    public QuizResponse getById(Long id){
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The quiz not found"));

        return quizMapper.toResponse(quiz);
    }

    //--------------------------------Get all the quizzes--------------------------------------------------
    public List<QuizResponse> getAll(){
        List<QuizResponse> quizzes = new ArrayList<>();

        for(Quiz quiz : quizRepository.findAll()){
            quizzes.add(quizMapper.toResponse(quiz));
        }

        return quizzes;
    }

    //--------------------------------Update the quiz---------------------------------------------------------
    @Transactional
    public QuizResponse update(Long id, QuizRequest request, Long userId, Authentication authentication)
        throws AccessDeniedException{
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The quiz does not exists"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if(!isAdmin && !quiz.getCreatedBy().equals(userId)){
            throw new AccessDeniedException("You do not have access to this quiz");
        };

        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setPassingScore(request.getPassingScore());
        quiz.setTimeLimit(request.getTimeLimit());

        Quiz saved = quizRepository.save(quiz);
        return quizMapper.toResponse(saved);
    }

    //----------------------------------Delete the quiz------------------------------------------------------
    @Transactional
    public void delete(Long id, Long userId, Authentication authentication) throws AccessDeniedException
    {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz does not exist"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if(!isAdmin && !quiz.getCreatedBy().equals(userId)){
            throw new AccessDeniedException("You do not own this quiz");
        }

        quizRepository.deleteById(id);
    }
}
