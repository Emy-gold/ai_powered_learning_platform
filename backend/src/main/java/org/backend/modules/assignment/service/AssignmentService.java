package org.backend.modules.assignment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Assignment;
import org.backend.domains.learning.Lesson;
import org.backend.modules.assignment.dto.AssignmentRequest;
import org.backend.modules.assignment.dto.AssignmentResponse;
import org.backend.modules.assignment.mapper.AssignmentMapper;
import org.backend.modules.assignment.repository.AssignmentRepository;
import org.backend.modules.lesson.repository.LessonRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentMapper assignmentMapper;

    //-------------------------------Create the assignment---------------------------------
    @Transactional
    public AssignmentResponse create(AssignmentRequest request){
        Assignment assignment = assignmentMapper.toEntity(request);

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        assignment.setLesson(lesson);

        return assignmentMapper.toResponse(assignmentRepository.save(assignment));
    }

    //-------------------------------Get assignment by id-----------------------------------
    public AssignmentResponse getById(Long id){
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        return assignmentMapper.toResponse(assignment);
    }

    //-------------------------------Get all assignments------------------------------------
    public List<AssignmentResponse> getAll(){
        List<AssignmentResponse> assignments = new ArrayList<>();
        for (Assignment assignment : assignmentRepository.findAll()){
            assignments.add(assignmentMapper.toResponse(assignment));
        }
        return assignments;
    }

    //-------------------------------Update the assignment----------------------------------
    @Transactional
    public AssignmentResponse update(Long id, AssignmentRequest request, Long userId, Authentication authentication){
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment is snot found"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin && !userId.equals(assignment.getCreatedBy())) {
            throw new AccessDeniedException("You do not own this assignment");
        }

        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setMaxScore(request.getMaxScore());
        assignment.setDueDate(request.getDueDate());

        return assignmentMapper.toResponse(assignmentRepository.save(assignment));
    }

    //-------------------------------Delete the assignment-----------------------------------
    @Transactional
    public void delete(Long id, Long userId, Authentication authentication){
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin && !userId.equals(assignment.getCreatedBy())) {
            throw new AccessDeniedException("You do not own this assignment");
        }

        assignmentRepository.delete(assignment);
    }
}