package tn.esprit.spring;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.*;

import java.time.LocalDate;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SkierServicesMockitoTest {
    @InjectMocks
    private SkierServicesImpl skierServices;

    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private ICourseRepository courseRepository;
    @Mock
    private IRegistrationRepository registrationRepository;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllSkiers() {
        List<Skier> skiers = new ArrayList<>();
        skiers.add(new Skier(1L, "Rayen", "Bourguiba", LocalDate.now(),"Zarzis", new Subscription(), new HashSet<Piste>(), new HashSet<Registration>()));
        skiers.add(new Skier(2L, "Mo7sen", "Bourguiba", LocalDate.now(),"Medenine", new Subscription(), new HashSet<Piste>(), new HashSet<Registration>()));
        when(skierRepository.findAll()).thenReturn(skiers);
        List<Skier> result = skierServices.retrieveAllSkiers();
        assertEquals(skiers, result);
    }

    @Test
    public void testAddCourse() {
        Skier skier = new Skier(3L,"Iheb", "Bourguiba", LocalDate.now(),"Ariana", new Subscription(), new HashSet<Piste>(), new HashSet<Registration>());
        when(skierRepository.save(skier)).thenReturn(skier);
        Skier result = skierServices.addSkier(skier);
        assertEquals(skier, result);
    }

    @Test
    public void testRetrieveCourse() {
        Long skierId = 4L;
        Skier skier = new Skier(4L,"Hassen", "Bourguiba", LocalDate.now(),"Tunis", new Subscription(), new HashSet<Piste>(), new HashSet<Registration>());
        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier));
        Skier result = skierServices.retrieveSkier(skierId);
        assertEquals(skier, result);
    }

    @Test
    public void testAddSkierAndAssignToCourse() {
        Skier skier = new Skier();
        skier.setRegistrations(new HashSet<>());
        Long numCourse = 1L;
        Course course = new Course();
        course.setNumCourse(numCourse);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        when(courseRepository.getById(numCourse)).thenReturn(course);
        Skier result = skierServices.addSkierAndAssignToCourse(skier, numCourse);
        verify(skierRepository, times(1)).save(skier);
        verify(courseRepository, times(1)).getById(numCourse);
        verify(registrationRepository, times(skier.getRegistrations().size())).save(any(Registration.class));
        assertTrue(result.getRegistrations().stream().allMatch(registration -> registration.getCourse().equals(course)));
    }
}

