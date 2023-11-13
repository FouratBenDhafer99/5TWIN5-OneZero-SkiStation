package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CourseServicesJUnitTest {

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllCourses() {
        List<Course> courses = new ArrayList<>();
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseServices.retrieveAllCourses();

        assertEquals(courses, result);
    }

    @Test
    public void testAddCourse() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseServices.addCourse(course);

        assertEquals(course, result);
    }

    @Test
    public void testUpdateCourse() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseServices.updateCourse(course);

        assertEquals(course, result);
    }

    @Test
    public void testRetrieveCourse() {
        Long courseId = 1L;
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.of(course));

        Course result = courseServices.retrieveCourse(courseId);

        assertEquals(course, result);
    }
}