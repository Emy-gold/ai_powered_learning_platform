package org.backend.modules.submission.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Assignment;
import org.backend.domains.learning.Submission;
import org.backend.domains.learning.submissionStatus;
import org.backend.domains.profile.StudentProfile;
import org.backend.modules.assignment.repository.AssignmentRepository;
import org.backend.modules.student.repository.StudentRepository;
import org.backend.modules.submission.dto.SubmissionRequest;
import org.backend.modules.submission.dto.SubmissionResponse;
import org.backend.modules.submission.mapper.SubmissionMapper;
import org.backend.modules.submission.repository.SubmissionRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentProfileRepository;
    private final SubmissionMapper submissionMapper;

    //-------------------------------Submit an assignment---------------------------------
    @Transactional
    public SubmissionResponse submit(SubmissionRequest request, Long userId){

        StudentProfile student = studentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        if (submissionRepository.findByAssignmentIdAndStudentId(assignment.getId(), student.getId()).isPresent()) {
            throw new RuntimeException("You have already submitted this assignment");
        }

        Submission submission = submissionMapper.toEntity(request);
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setStatus(submissionStatus.PENDING);

        return submissionMapper.toResponse(submissionRepository.save(submission));
    }

    //-------------------------------Get submission by id, scoped to owner----------------
    public SubmissionResponse getById(Long id, Long userId){
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        if (!submission.getStudent().getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not own this submission");
        }

        return submissionMapper.toResponse(submission);
    }

    //-------------------------------Get all submissions for the current student----------
    public List<SubmissionResponse> getMySubmissions(Long userId){
        StudentProfile student = studentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        List<SubmissionResponse> responses = new ArrayList<>();
        for (Submission submission : submissionRepository.findByStudentId(student.getId())) {
            responses.add(submissionMapper.toResponse(submission));
        }
        return responses;
    }
}
