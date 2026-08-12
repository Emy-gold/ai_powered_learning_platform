package org.backend.modules.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.profile.StudentProfile;
import org.backend.domains.user.User;
import org.backend.modules.student.dto.StudentRequest;
import org.backend.modules.student.dto.StudentResponse;
import org.backend.modules.student.dto.StudentUpdateRequest;
import org.backend.modules.student.mapper.StudentMapper;
import org.backend.modules.student.repository.StudentRepository;
import org.backend.modules.user.repositories.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;


    //--------------------Save the student profile--------------
    @Transactional
    public StudentResponse create(Long userId, StudentRequest request){
        if(studentRepository.existsByUserId(userId)){
            throw new RuntimeException("Student profile already exists for this user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile studentProfile = studentMapper.toEntity(request);
        studentProfile.setUser(user);

        return studentMapper.toResponse(studentRepository.save(studentProfile));
    }

    //--------------------------Get the student by id------------------------------
    public StudentResponse getById(Long id){
        StudentProfile studentProfile = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("student not found"));
        return studentMapper.toResponse(studentProfile);
    }

    //--------------------------Get the student by user id-------------------------
    public StudentResponse getByUserId(Long userId){
        StudentProfile studentProfile = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student user not found"));
        return studentMapper.toResponse(studentProfile);
    }

    //--------------------------Get all the students profiles-----------------------
    public List<StudentResponse> getAll(){
        List<StudentResponse> students = new ArrayList<>();

        for(StudentProfile student: studentRepository.findAll()){
            students.add(studentMapper.toResponse(student));
        }

        return students;
    }

    //------------------------Update the student profile------------------------------
    @Transactional
    public StudentResponse update(Long id, StudentUpdateRequest request){
        StudentProfile studentProfile = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        //Update the student fields
        studentProfile.setInstitution(request.getInstitution());
        studentProfile.setFieldOfStudy(request.getFieldOfStudy());
        studentProfile.setLearningGoal(request.getLearningGoal());
        studentProfile.setEducationLevel(request.getEducationLevel());

        //Update the user fields
        User user = studentProfile.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return studentMapper.toResponse(studentProfile);
    }

    //--------------------Delete the student profile--------------------
    @Transactional
    public void delete(Long id){
        if (!studentRepository.existsById(id)){
            throw new RuntimeException("Student profile does not exist");
        }

        studentRepository.deleteById(id);
    }
}
