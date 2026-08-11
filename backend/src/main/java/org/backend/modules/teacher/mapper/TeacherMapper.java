package org.backend.modules.teacher.mapper;

import org.backend.domains.profile.TeacherProfile;
import org.backend.modules.teacher.dto.TeacherRequest;
import org.backend.modules.teacher.dto.TeacherResponse;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public TeacherResponse toResponse(TeacherProfile teacher){

        return TeacherResponse.builder()
                .id(teacher.getId())
                .email(teacher.getUser().getEmail())
                .createdAt(teacher.getCreatedAt())
                .fullName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName())
                .experience(teacher.getExperience())
                .speciality(teacher.getSpeciality())
                .education(teacher.getEducation())
                .userId(teacher.getUser().getId())
                .build();
    }

    public TeacherProfile toEntity(TeacherRequest request){
        TeacherProfile teacher = new TeacherProfile();

        teacher.setExperience(request.getExperience());
        teacher.setSpeciality(request.getSpeciality());
        teacher.setEducation(request.getEducation());

        return teacher;
    }
}
