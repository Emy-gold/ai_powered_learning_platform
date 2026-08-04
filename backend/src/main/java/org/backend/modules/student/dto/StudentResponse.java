package org.backend.modules.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.profile.EducationLevel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;

    private String email;

    private String fullName;

    private String institution;

    private String fieldOfStudy;

    private String learningGoal;

    private EducationLevel educationLevel;


}
