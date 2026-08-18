package org.backend.modules.course.service;

import jakarta.transaction.Transactional;
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
        TeacherProfile teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Course course = new Course();

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setLevel(request.getLevel());
        course.setLanguage(request.getLanguage());
        course.setTeacher(teacher);
        course.setCategory(category);

        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponse(savedCourse);
    }

    //----------------------Get the course by teacher id-----------------------------------
    public List<CourseResponse> getByTeacherId(Long teacherId){
        return courseRepository.findByTeacherId(teacherId)
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    //----------------------Get course by id-----------------------------------------------
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

    //---------------------Update the course -------------------------------------------------
    @Transactional
    public CourseResponse update(Long id, CourseRequest request){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Category category = categoryRepository.findById(id)
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
    public void delete(Long id){
        if(!courseRepository.existsById(id)){
            throw new RuntimeException("Course does not exists");
        }
        courseRepository.deleteById(id);
    }
}
