package org.backend.modules.teacher.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.profile.TeacherProfile;
import org.backend.domains.user.User;
import org.backend.modules.student.mapper.StudentMapper;
import org.backend.modules.student.repository.StudentRepository;
import org.backend.modules.teacher.dto.TeacherRequest;
import org.backend.modules.teacher.dto.TeacherResponse;
import org.backend.modules.teacher.dto.TeacherUpdateRequest;
import org.backend.modules.teacher.mapper.TeacherMapper;
import org.backend.modules.teacher.repository.TeacherRepository;
import org.backend.modules.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    //-----------------------------Save the teacher profile---------------------------
    @Transactional
    public TeacherResponse create(Long userId, TeacherRequest request){
        if(teacherRepository.existsByUserId(userId)){
            throw new RuntimeException("Student profile already exists for this user");
        }
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

            TeacherProfile teacherProfile = teacherMapper.toEntity(request);
            teacherProfile.setUser(user);
            return teacherMapper.toResponse(teacherRepository.save(teacherProfile));

    }

    //---------------------------Get the teacher by id -----------------------------------
    public TeacherResponse getById(Long id){
        TeacherProfile teacherProfile = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("teacher not found"));
        return teacherMapper.toResponse(teacherProfile);
    }

    //----------------------------Get the teacher by user id -------------------------------
    public TeacherResponse getByUserId(Long userId){
        TeacherProfile teacherProfile = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return  teacherMapper.toResponse(teacherProfile);
    }

    //--------------------------Get all the teachers profiles--------------------------------
    public List<TeacherResponse> getAll(){
        List<TeacherResponse> teachers = new ArrayList<>();
        for(TeacherProfile teacher : teacherRepository.findAll()){
            teachers.add(teacherMapper.toResponse(teacher));
        }
        return teachers;
    }

    //------------------------Update the teacher profile ------------------------------------
    @Transactional
    public TeacherResponse update(Long id, TeacherUpdateRequest request){
        TeacherProfile teacherProfile = teacherRepository.findById(id)
                .orElseThrow(() -> new  RuntimeException("Teacher profile not found"));

        //Update the teacher fields
        teacherProfile.setExperience(request.getExperience());
        teacherProfile.setSpeciality(request.getSpeciality());
        teacherProfile.setEducation(request.getEducation());

        //Update the user fields
        User user = teacherProfile.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return teacherMapper.toResponse(teacherProfile);
    }

    //-------------------------Delete the teacher profile-----------------------------------
    @Transactional
    public void delete(Long id){
        if(!teacherRepository.existsById(id)){
            throw new RuntimeException("Teacher profile does not exist");
        }
        teacherRepository.deleteById(id);
    }
}
