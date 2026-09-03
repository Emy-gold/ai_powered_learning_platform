package org.backend.modules.answer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.assessment.Answer;
import org.backend.domains.assessment.Question;
import org.backend.modules.answer.dto.AnswerRequest;
import org.backend.modules.answer.dto.AnswerResponse;
import org.backend.modules.answer.mapper.AnswerMapper;
import org.backend.modules.answer.repository.AnswerRepository;
import org.backend.modules.question.repository.QuestionRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final QuestionRepository questionRepository;

    //-------------------------------------------Create the answer------------------------------------------------------
    @Transactional
    public AnswerResponse create(AnswerRequest request){

        Answer answer = answerMapper.toEntity(request);
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("The question not found"));

        answer.setQuestion(question);
        return answerMapper.toResponse(answerRepository.save(answer));
    }

    //-------------------------------------Get the answer by id---------------------------------------------------------
    public AnswerResponse getById(Long id){
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The answer not found"));

        return answerMapper.toResponse(answer);
    }

    //------------------------------------Get all the answers-----------------------------------------------------------
    public List<AnswerResponse> getAll(){
        List<AnswerResponse> answers = new ArrayList<>();

        for(Answer answer : answerRepository.findAll()){
            answers.add(answerMapper.toResponse(answer));
        }

        return answers;
    }

    //-----------------------------------Update the answer--------------------------------------------------------------
    @Transactional
    public AnswerResponse update(Long id, AnswerRequest request, Long userId, Authentication authentication)
        throws AccessDeniedException{

        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The answer of this question is not found"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if(!isAdmin && !answer.getCreatedBy().equals(userId)){
            throw new AccessDeniedException("You dont have access to this answer");
        }

        answer.setAnswerText(request.getAnswerText());
        answer.setCorrect(request.isCorrect());

        Answer saved = answerRepository.save(answer);
        return answerMapper.toResponse(saved);
    }

    //--------------------------------Delete the answer-----------------------------------------------------------------
    @Transactional
    public void delete(Long id, Long userId , Authentication authentication)
        throws AccessDeniedException{

        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The answer is not found"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if(!isAdmin && !answer.getCreatedBy().equals(userId)){
            throw new AccessDeniedException("You dont have access to this answer");
        }

        answerRepository.deleteById(id);
    }
}
