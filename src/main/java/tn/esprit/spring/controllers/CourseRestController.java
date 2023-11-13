package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.DTOs.*;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.services.ICourseServices;

import java.util.List;
import java.util.stream.*;

@Tag(name = "\uD83D\uDCDA Course Management")
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseRestController {
    
    private final ICourseServices courseServices;

    @Operation(description = "Add Course")
    @PostMapping("/add")
    public CourseDTO addCourse(@RequestBody CourseDTO courseDTO){
        Course course = convertToEntity(courseDTO);
        Course addedCourse = courseServices.addCourse(course);

        return convertToDTO(addedCourse);
    }

    @Operation(description = "Retrieve all Courses")
    @GetMapping("/all")
    public List<CourseDTO> getAllCourses(){
        List<Course> courses = courseServices.retrieveAllCourses();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Operation(description = "Update Course ")
    @PutMapping("/update")
    public CourseDTO updateCourse(@RequestBody CourseDTO courseDTO){
        Course course = convertToEntity(courseDTO);
        Course updatedCourse = courseServices.updateCourse(course);

        return convertToDTO(updatedCourse);
    }
    @Operation(description = "Retrieve Course by Id")
    @GetMapping("/get/{id-course}")
    public CourseDTO getById(@PathVariable("id-course") Long numCourse){
        Course course = courseServices.retrieveCourse(numCourse);

        return convertToDTO(course);
    }

    // Helper method to convert Course entity to CourseDTO
    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setLevel(course.getLevel());
        courseDTO.setTypeCourse(course.getTypeCourse());
        courseDTO.setSupport(course.getSupport());
        courseDTO.setPrice(course.getPrice());
        courseDTO.setTimeSlot(course.getTimeSlot());
        return courseDTO;
    }

    // Helper method to convert CourseDTO to Course entity
    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setLevel(courseDTO.getLevel());
        course.setTypeCourse(courseDTO.getTypeCourse());
        course.setSupport(courseDTO.getSupport());
        course.setPrice(courseDTO.getPrice());
        course.setTimeSlot(courseDTO.getTimeSlot());
        return course;
    }
}
