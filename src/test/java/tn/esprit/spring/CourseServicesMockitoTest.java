package tn.esprit.spring;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CourseServicesMockitoTest {

    @InjectMocks
    private CourseServicesImpl courseServices;

    @Mock
    private ICourseRepository courseRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1L, 1, TypeCourse.COLLECTIVE_CHILDREN, Support.SKI, 100.0f, 60, new HashSet<>()));
        courses.add(new Course(2L, 2, TypeCourse.INDIVIDUAL, Support.SNOWBOARD, 150.0f, 90, new HashSet<>()));
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseServices.retrieveAllCourses();

        assertEquals(courses, result);
    }

    @Test
    public void testAddCourse() {
        Course course = new Course(3L, 3, TypeCourse.INDIVIDUAL, Support.SKI, 120.0f, 75, new HashSet<>());
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseServices.addCourse(course);

        assertEquals(course, result);
    }

    @Test
    public void testUpdateCourse() {
        Course existingCourse = new Course(4L, 1, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 100.0f, 60, new HashSet<>());
        Course updatedCourse = new Course(4L, 2, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 150.0f, 90, new HashSet<>());

        when(courseRepository.save(updatedCourse)).thenReturn(updatedCourse);
        when(courseRepository.findById(existingCourse.getNumCourse())).thenReturn(Optional.of(existingCourse));

        Course result = courseServices.updateCourse(updatedCourse);

        assertEquals(updatedCourse, result);
    }

    @Test
    public void testRetrieveCourse() {
        Long courseId = 5L;
        Course course = new Course(5L, 1, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 60, new HashSet<>());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course result = courseServices.retrieveCourse(courseId);

        assertEquals(course, result);
    }
}
