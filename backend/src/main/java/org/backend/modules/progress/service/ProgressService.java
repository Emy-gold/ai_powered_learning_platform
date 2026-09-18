package org.backend.modules.progress.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Course;
import org.backend.domains.learning.Lesson;
import org.backend.domains.learning.Progress;
import org.backend.domains.profile.StudentProfile;
import org.backend.modules.lesson.repository.LessonRepository;
import org.backend.modules.progress.dto.ProgressResponse;
import org.backend.modules.progress.mapper.ProgressMapper;
import org.backend.modules.progress.repository.ProgressRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final LessonRepository lessonRepository;
    private final ProgressMapper progressMapper;

    //---------------------Initialize progress rows when a student enrolls------------
    // called from EnrollmentService.enroll() — one Progress row per lesson in the course
    @Transactional
    public void initializeForEnrollment(StudentProfile student, Course course, List<Lesson> lessons){
        List<Progress> rows = new ArrayList<>();
        for (Lesson lesson : lessons) {
            rows.add(Progress.builder()
                    .student(student)
                    .course(course)
                    .lesson(lesson)
                    .progressPercentage(0)
                    .completed(false)
                    .build());
        }
        progressRepository.saveAll(rows);
    }

    //---------------------Mark a lesson accessed / update percentage-----------------
    @Transactional
    public ProgressResponse updateProgress(Long lessonId, Integer progressPercentage, Long userId){

        Progress progress = progressRepository.findByStudentIdAndLessonId(userId, lessonId)
                .orElseThrow(() -> new RuntimeException("Progress record not found"));

        if (!progress.getStudent().getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not own this progress record");
        }

        progress.setProgressPercentage(progressPercentage);
        progress.setCompleted(progressPercentage >= 100);
        progress.setLastAccessedAt(LocalDateTime.now());

        return progressMapper.toResponse(progressRepository.save(progress));
    }

    //---------------------Get all progress for the current student in a course-------
    public List<ProgressResponse> getByCourse(Long studentId, Long courseId){
        List<ProgressResponse> responses = new ArrayList<>();
        for (Progress progress : progressRepository.findByStudentIdAndCourseId(studentId, courseId)) {
            responses.add(progressMapper.toResponse(progress));
        }
        return responses;
    }
}