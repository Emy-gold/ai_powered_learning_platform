package org.backend.modules.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.profile.StudentProfile;
import org.backend.domains.user.User;
import org.backend.modules.student.dto.StudentRequest;
import org.backend.modules.student.dto.StudentResponse;
import org.backend.modules.student.mapper.StudentMapper;
import org.backend.modules.student.repository.StudentRepository;
import org.backend.modules.user.repositories.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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

    //--------------------------Get the user by id------------------------------
    public StudentResponse getById(Long id){
        StudentProfile studentProfile = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("student not found"));
        return studentMapper.toResponse(studentProfile);
    }
}
