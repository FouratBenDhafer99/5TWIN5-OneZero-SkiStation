package tn.esprit.spring.RegistrationTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.InstructorServicesImpl;
import tn.esprit.spring.services.RegistrationServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RegistrationServicesJUnitTest {

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private ICourseRepository courseRepository;

    @Before()
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddRegistrationAndAssignToSkier() {
        Long skierId = 1L;
        Skier skier = new Skier();
        skier.setNumSkier(skierId);

        Registration registration = new Registration();
        registration.setSkier(null);

        when(skierRepository.findById(skierId)).thenReturn(java.util.Optional.ofNullable(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, skierId);

        assertNotNull(result);
        assertEquals(skier, result.getSkier());

    }

    @Test
    public void testAddRegistrationAndAssignToSkierSkierNotFound() {
        Long skierId = 1L;
        when(skierRepository.findById(skierId)).thenReturn(java.util.Optional.empty());

        Registration result = registrationServices.addRegistrationAndAssignToSkier(new Registration(), skierId);

        assertNull(result);
    }

    @Test
    public void testAssignRegistrationToCourse() {
        Long registrationId = 1L;
        Long courseId = 2L;
        Registration registration = new Registration();
        registration.setNumRegistration(registrationId);

        Course course = new Course();
        course.setNumCourse(courseId);

        when(registrationRepository.findById(registrationId)).thenReturn(java.util.Optional.ofNullable(registration));
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.ofNullable(course));
        when(registrationRepository.save(registration)).thenReturn(registration);
        Registration result = registrationServices.assignRegistrationToCourse(registrationId, courseId);
        assertNotNull(result);
        assertEquals(course, result.getCourse());
    }

    @Test
    public void testAssignRegistrationToCourseRegistrationNotFound() {
        Long registrationId = 1L;
        Long courseId = 2L;
        when(registrationRepository.findById(registrationId)).thenReturn(java.util.Optional.empty());
        Registration result = registrationServices.assignRegistrationToCourse(registrationId, courseId);
        assertNull(result);
    }

    @Test
    public void testAssignRegistrationToCourseCourseNotFound() {
        Long registrationId = 1L;
        Long courseId = 2L;
        Registration registration = new Registration();
        registration.setNumRegistration(registrationId);
        when(registrationRepository.findById(registrationId)).thenReturn(java.util.Optional.ofNullable(registration));
        when(courseRepository.findById(courseId)).thenReturn(java.util.Optional.empty());
        Registration result = registrationServices.assignRegistrationToCourse(registrationId, courseId);
        assertNull(result);
    }

    @Test
    public void testAddRegistrationAndAssignToSkierAndCourse() {
        Long numSkier = 1L;
        Long numCourse = 2L;

        Course course = new Course(1L, 2, TypeCourse.INDIVIDUAL, Support.SKI, 100.0f, 3, new HashSet<>());
        Skier skier = new Skier(1L, "John", "Doe", LocalDate.of(1990, 5, 15), "City", new Subscription(), new HashSet<>(), new HashSet<>());
        Registration registration1 = new Registration(1L, 2, skier, course);
        Registration registration = new Registration();
        registration.setNumWeek(2);
        registration.setSkier(skier);
        registration.setCourse(course);

        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(registration.getNumWeek(), skier.getNumSkier(), course.getNumCourse())).thenReturn(0L);

        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, numSkier, numCourse);

        assertNull(result);
    }

}
