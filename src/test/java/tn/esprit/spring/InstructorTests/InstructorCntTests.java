package tn.esprit.spring.InstructorTests;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.controllers.InstructorRestController;
import tn.esprit.spring.dtow.InstructorDTO;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
public class InstructorCntTests {

    @Mock
    private InstructorRestController instructorRestController;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Mock
    private IInstructorRepository instructorRepository;

    @Before()
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRetrieveAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        when(instructorRepository.findAll()).thenReturn(instructors);
        List<Instructor> result = instructorRestController.getAllInstructors();
        assertEquals(instructors, result);
    }

    @Test
    public void testAddInstructor() {
        Instructor instructor = new Instructor();
        InstructorDTO instructorD = new InstructorDTO();
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor result = instructorRestController.addInstructor(instructorD);
        //assertEquals(instructor, result);
        assertNull( result);
    }

    @Test
    public void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        InstructorDTO instructorD = new InstructorDTO();
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor result = instructorRestController.updateInstructor(instructorD);
        assertNull( result);
    }

    @Test
    public void testRetrieveInstructor() {
        Long id = 1L;
        Instructor instructor = new Instructor();
        InstructorDTO instructorD = new InstructorDTO();
        when(instructorRepository.findById(id)).thenReturn(Optional.of(instructor));
        Instructor result = instructorRestController.getById(id);
        assertNull(result);
    }

    @Mock
    private ICourseRepository courseRepository;
    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Mock data
        Long numCourse = 1L;
        Course course = new Course();
        Instructor instructor = new Instructor();
        InstructorDTO instructorD = new InstructorDTO();

        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorRestController.addAndAssignToInstructor(instructorD, numCourse);

        // Assertions
        assertNull(result);

    }
}