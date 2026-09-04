package org.backend.modules.assignment.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Assignment;
import org.backend.modules.assignment.dto.AssignmentRequest;
import org.backend.modules.assignment.dto.AssignmentResponse;
import org.backend.modules.lesson.mapper.LessonMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentMapper {

    private final LessonMapper lessonMapper;

    public AssignmentResponse toResponse(Assignment assignment){


        return AssignmentResponse.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .maxScore(assignment.getMaxScore())
                .dueDate(assignment.getDueDate())
                .lesson(lessonMapper.toResponse(assignment.getLesson()))
                .build();
    }

    public Assignment toEntity(AssignmentRequest request){

        Assignment assignment = new Assignment();

        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setMaxScore(request.getMaxScore());
        assignment.setDueDate(request.getDueDate());

        return assignment;

    }
}
