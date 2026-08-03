package org.backend.modules.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.learning.Course;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {

    private Long id;
    private String email;
    private LocalDateTime createdAt;
    private String fullName;
    private int experience;
    private String speciality;
    private String education;
    private List<CourseResponse> courses;
    private Long userId;
}
