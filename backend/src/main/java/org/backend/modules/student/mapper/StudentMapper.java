package org.backend.modules.student.mapper;

import org.backend.domains.profile.StudentProfile;
import org.backend.modules.student.dto.StudentRequest;
import org.backend.modules.student.dto.StudentResponse;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentResponse toResponse(StudentProfile student){

        return StudentResponse.builder()
                .id(student.getId())
                .email(student.getUser().getEmail())
                .fullName(student.getUser().getFirstName() + " "+ student.getUser().getLastName())
                .institution(student.getInstitution())
                .fieldOfStudy(student.getFieldOfStudy())
                .learningGoal(student.getLearningGoal())
                .educationLevel(student.getEducationLevel())
                .userId(student.getUser().getId())
                .build();
    }

    public StudentProfile toEntity(StudentRequest request){
        StudentProfile student = new StudentProfile();

        student.setInstitution(request.getInstitution());
        student.setFieldOfStudy(request.getFieldOfStudy());
        student.setLearningGoal(request.getLearningGoal());
        student.setEducationLevel(request.getEducationLevel());

        return student;
    }
}
