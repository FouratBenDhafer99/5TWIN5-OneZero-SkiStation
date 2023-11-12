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
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class SkierServicesJUnitTest {
    @InjectMocks
    private SkierServicesImpl skierServices;

    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private  IPisteRepository pisteRepository;
    @Mock
    private  ISubscriptionRepository subscriptionRepository;
    @Mock
    private  ICourseRepository courseRepository;
    @Mock
    private  IRegistrationRepository registrationRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllSkiers() {
        List<Skier> skiers = new ArrayList<>();
        when(skierRepository.findAll()).thenReturn(skiers);
        List<Skier> result = skierServices.retrieveAllSkiers();
        assertEquals(skiers, result);
    }

//    @Test
//    public void testAddSkier() {
//        Skier skier = new Skier(1L, "Rayen", "Bourguiba", LocalDate.now(),"Zarzis", new Subscription(), new HashSet<Piste>(), new HashSet<Registration>());
//        when(skierRepository.save(skier)).thenReturn(skier);
//        Skier result = skierServices.addSkier(skier);
//        assertEquals(skier, result);
//    }

    @Test
    public void testAddSkierWithAnnualSubscription() {
        Skier skier = createSkierWithSubscription(TypeSubscription.ANNUAL);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier savedSkier = skierServices.addSkier(skier);
        verify(skierRepository, times(1)).save(skier);
        reset(skierRepository);
    }

    @Test
    public void testAddSkierWithSemestrialSubscription() {
        Skier skier = createSkierWithSubscription(TypeSubscription.SEMESTRIEL);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier savedSkier = skierServices.addSkier(skier);
        verify(skierRepository, times(1)).save(skier);
        reset(skierRepository);
    }

    @Test
    public void testAddSkierWithMonthlySubscription() {
        Skier skier = createSkierWithSubscription(TypeSubscription.MONTHLY);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier savedSkier = skierServices.addSkier(skier);
        verify(skierRepository, times(1)).save(skier);
        reset(skierRepository);
    }

    private Skier createSkierWithSubscription(TypeSubscription typeSubscription) {
        Skier skier = new Skier();
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setDateOfBirth(LocalDate.of(1990, 1, 1));
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(typeSubscription);
        skier.setSubscription(subscription);
        return skier;
    }

    @Test
    public void testRetrieveSkier() {
        Long skierId = 1L;
        Skier skier = new Skier();
        when(skierRepository.findById(skierId)).thenReturn(java.util.Optional.of(skier));
        Skier result = skierServices.retrieveSkier(skierId);
        assertEquals(skier, result);
    }

    @Test
    public void testAssignSkierToSubscription() {
        Long numSkier = 1L;
        Long numSubscription = 2L;
        Skier skier = new Skier();
        skier.setNumSkier(numSkier);
        Subscription subscription = new Subscription();
        subscription.setNumSub(numSubscription);
        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(numSubscription)).thenReturn(Optional.of(subscription));
        Skier updatedSkier = skierServices.assignSkierToSubscription(numSkier, numSubscription);
        verify(skierRepository, times(1)).findById(numSkier);
        verify(subscriptionRepository, times(1)).findById(numSubscription);
        verify(skierRepository, times(1)).save(skier);
        assertEquals(numSubscription, updatedSkier.getSubscription().getNumSub());
        reset(skierRepository, subscriptionRepository);
    }

    @Test
    public void testAddSkierAndAssignToCourse() {
        Long numCourse = 1L;
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        Course course = new Course();
        course.setNumCourse(numCourse);
        Set<Registration> registrations = new HashSet<>();
        Registration registration = new Registration();
        registration.setNumRegistration(1L);
        registrations.add(registration);
        skier.setRegistrations(registrations);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        when(courseRepository.getById(numCourse)).thenReturn(course);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        Skier savedSkier = skierServices.addSkierAndAssignToCourse(skier, numCourse);
        verify(skierRepository, times(1)).save(skier);
        verify(courseRepository, times(1)).getById(numCourse);
        verify(registrationRepository, times(1)).save(registration);
        assertEquals(numCourse, registration.getCourse().getNumCourse());
        assertEquals(skier, registration.getSkier());
        reset(skierRepository, courseRepository, registrationRepository);
    }

    @Test
    public void testRemoveSkier() {
        Long numSkier = 1L;
        skierServices.removeSkier(numSkier);
        verify(skierRepository, times(1)).deleteById(numSkier);
        reset(skierRepository);
    }

    @Test
    public void testAssignSkierToPiste() {
        Long numSkier = 1L;
        Long numPiste = 2L;
        Skier skier = new Skier();
        skier.setNumSkier(numSkier);
        Piste piste = new Piste();
        piste.setNumPiste(numPiste);
        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.of(piste));
        Skier updatedSkier = skierServices.assignSkierToPiste(numSkier, numPiste);
        verify(skierRepository, times(1)).findById(numSkier);
        verify(pisteRepository, times(1)).findById(numPiste);
        verify(skierRepository, times(1)).save(skier);
        assertEquals(numPiste, updatedSkier.getPistes().iterator().next().getNumPiste());
        reset(skierRepository, pisteRepository);
    }

    @Test
    public void testRetrieveSkiersBySubscriptionType() {
        TypeSubscription typeSubscription = TypeSubscription.ANNUAL;
        Skier skier1 = new Skier();
        skier1.setNumSkier(1L);
        Skier skier2 = new Skier();
        skier2.setNumSkier(2L);
        List<Skier> skiersWithSubscription = Arrays.asList(skier1, skier2);
        when(skierRepository.findBySubscription_TypeSub(typeSubscription)).thenReturn(skiersWithSubscription);
        List<Skier> retrievedSkiers = skierServices.retrieveSkiersBySubscriptionType(typeSubscription);
        verify(skierRepository, times(1)).findBySubscription_TypeSub(typeSubscription);
        assertEquals(2, retrievedSkiers.size());
        reset(skierRepository);
    }
}
