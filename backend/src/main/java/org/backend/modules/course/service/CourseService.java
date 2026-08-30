package org.backend.modules.course.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Category;
import org.backend.domains.learning.Course;
import org.backend.domains.profile.TeacherProfile;
import org.backend.modules.category.repository.CategoryRepository;
import org.backend.modules.course.dto.CourseRequest;
import org.backend.modules.course.dto.CourseResponse;
import org.backend.modules.course.mapper.CourseMapper;
import org.backend.modules.course.repository.CourseRepository;
import org.backend.modules.teacher.repository.TeacherRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final TeacherRepository teacherRepository;
    private final CategoryRepository categoryRepository;

    //--------------------------Create the course function-----------------------------
    @Transactional
    public CourseResponse create(CourseRequest request){

        Course course = courseMapper.toEntity(request);

        TeacherProfile teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        course.setTeacher(teacher);
        course.setCategory(category);

        return courseMapper.toResponse(courseRepository.save(course));
    }

    //----------------------Get the course by teacher id-----------------------------------
    @Transactional(readOnly = true)
    public List<CourseResponse> getByTeacherId(Long teacherId){
        return courseRepository.findByTeacherId(teacherId)
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    //-----------------------My courses-------------------------------------------------------
    @Transactional(readOnly = true)
    public List<CourseResponse> getMyCourses() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        TeacherProfile teacher = teacherRepository.findByUserEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Teacher profile not found"));

        return courseRepository.findByTeacherId(teacher.getId())
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    //----------------------Get course by id-----------------------------------------------
    @Transactional(readOnly = true)
    public CourseResponse getById(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return courseMapper.toResponse(course);
    }

    //---------------------Get all courses--------------------------------------------------
    public List<CourseResponse> getAll(){
        List<CourseResponse> courses = new ArrayList<>();
        for(Course course : courseRepository.findAll()){
            courses.add(courseMapper.toResponse(course));
        }

        return courses;
    }
    //--------------------------Search by title---------------------------------------------
    @Transactional(readOnly = true)
    public List<CourseResponse> searchByTitle(String title) {

        return courseRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    //---------------------Update the course -------------------------------------------------
    @Transactional
    public CourseResponse update(Long id, CourseRequest request, Long userId, Authentication authentication )
        throws AccessDeniedException {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if(!isAdmin && !course.getCreatedBy().equals(userId)){
            throw new AccessDeniedException("You dont have access to this course");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setLevel(request.getLevel());
        course.setLanguage(request.getLanguage());
        course.setCategory(category);

        Course saved = courseRepository.save(course);
        return courseMapper.toResponse(saved);
    }

    //----------------------Delete the course ----------------------------------------
    @Transactional
    public void delete(Long id , Long userId, Authentication authentication ) throws  AccessDeniedException
    {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course does not exist"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin && !course.getCreatedBy().equals(userId)) {
            throw new AccessDeniedException("You do not own this course");
        }
        courseRepository.deleteById(id);
    }
}
