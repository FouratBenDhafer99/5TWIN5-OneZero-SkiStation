package tn.esprit.spring;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.dto.SkierDTO;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

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

    @Test
    public void testAddSkierWithAnnualSubscription() {
        Skier skier = createSkierWithSubscription(TypeSubscription.ANNUAL);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier savedSkier = skierServices.addSkier(skier);
        verify(skierRepository, times(1)).save(skier);
        reset(skierRepository);
        assertEquals(skier.getSubscription().getStartDate().plusYears(1), skier.getSubscription().getEndDate());
    }

    @Test
    public void testAddSkierWithSemestrialSubscription() {
        Skier skier = createSkierWithSubscription(TypeSubscription.SEMESTRIEL);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier savedSkier = skierServices.addSkier(skier);
        verify(skierRepository, times(1)).save(skier);
        reset(skierRepository);
        assertEquals(skier.getSubscription().getStartDate().plusMonths(6), skier.getSubscription().getEndDate());
    }

    @Test
    public void testAddSkierWithMonthlySubscription() {
        Skier skier = createSkierWithSubscription(TypeSubscription.MONTHLY);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier savedSkier = skierServices.addSkier(skier);
        verify(skierRepository, times(1)).save(skier);
        reset(skierRepository);
        assertEquals(skier.getSubscription().getStartDate().plusMonths(1), skier.getSubscription().getEndDate());
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

    @Test
    public void testAssignSkierToSubscription() {
        Long numSkier = 1L;
        Long numSubscription = 1L;

        // Create a Skier instance
        Skier skier = new Skier();
        skier.setNumSkier(numSkier);  // Set other necessary fields

        // Create a Subscription instance
        Subscription subscription = new Subscription();
        subscription.setNumSub(numSubscription);  // Set other necessary fields

        when(skierRepository.findById(eq(numSkier))).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(eq(numSubscription))).thenReturn(Optional.of(subscription));

        // Logging added for troubleshooting
        System.out.println("Before assignSkierToSubscription");

        Skier result = skierServices.assignSkierToSubscription(numSkier, numSubscription);

        System.out.println("After assignSkierToSubscription");

        if (result == null) {
            System.out.println("Result is null");
            System.out.println("Skier: " + skier);
            System.out.println("Subscription: " + subscription);
        }

        assertNotNull(result);
        verify(skierRepository, times(1)).findById(eq(numSkier));
        verify(subscriptionRepository, times(1)).findById(eq(numSubscription));
        verify(skierRepository, times(1)).save(eq(skier));
    }


    @Test
    public void testAssignSkierToPiste() {
        Long numSkier = 1L;
        Long numPiste = 2L;
        Skier skier = new Skier();
        Piste piste = new Piste();
        when(skierRepository.findById(numSkier)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.of(piste));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier result = skierServices.assignSkierToPiste(numSkier, numPiste);
        verify(skierRepository, times(1)).findById(numSkier);
        verify(pisteRepository, times(1)).findById(numPiste);
        verify(skierRepository, times(1)).save(skier);
        assertTrue(result.getPistes().contains(piste));
    }

    @Test
    public void testSkierFields() {
        Long numSkier = 1L;
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String city = "ExampleCity";
        Skier skier = new Skier();
        skier.setNumSkier(numSkier);
        skier.setFirstName(firstName);
        skier.setLastName(lastName);
        skier.setDateOfBirth(dateOfBirth);
        skier.setCity(city);
        assertEquals(numSkier, skier.getNumSkier());
        assertEquals(firstName, skier.getFirstName());
        assertEquals(lastName, skier.getLastName());
        assertEquals(dateOfBirth, skier.getDateOfBirth());
        assertEquals(city, skier.getCity());
    }


    @Test
    public void testSkierDTOConstructor() {
        SkierDTO skierDTO = new SkierDTO();
        assertNotNull(skierDTO);
    }

    @Test
    public void testSkierDTOFields() {
        Long numSkier = 1L;
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String city = "ExampleCity";
        Subscription subscription = new Subscription(); // Make sure to handle Subscription tests separately
        Set<Piste> pistes = new HashSet<>(); // Make sure to handle Piste tests separately
        Set<Registration> registrations = new HashSet<>(); // Make sure to handle Registration tests separately

        SkierDTO skierDTO = new SkierDTO();
        skierDTO.setNumSkier(numSkier);
        skierDTO.setFirstName(firstName);
        skierDTO.setLastName(lastName);
        skierDTO.setDateOfBirth(dateOfBirth);
        skierDTO.setCity(city);
        skierDTO.setSubscription(subscription);
        skierDTO.setPistes(pistes);
        skierDTO.setRegistrations(registrations);

        assertEquals(numSkier, skierDTO.getNumSkier());
        assertEquals(firstName, skierDTO.getFirstName());
        assertEquals(lastName, skierDTO.getLastName());
        assertEquals(dateOfBirth, skierDTO.getDateOfBirth());
        assertEquals(city, skierDTO.getCity());
        assertEquals(subscription, skierDTO.getSubscription());
        assertEquals(pistes, skierDTO.getPistes());
        assertEquals(registrations, skierDTO.getRegistrations());
    }



    private SkierDTO createSkierDTO() {
        SkierDTO skierDTO = new SkierDTO();
        skierDTO.setNumSkier(1L);
        skierDTO.setFirstName("John");
        skierDTO.setLastName("Doe");
        skierDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        skierDTO.setCity("ExampleCity");
        skierDTO.setSubscription(new Subscription());
        skierDTO.setPistes(new HashSet<>());
        skierDTO.setRegistrations(new HashSet<>());
        return skierDTO;
    }

    private Skier createSkier() {
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setDateOfBirth(LocalDate.of(1990, 1, 1));
        skier.setCity("ExampleCity");
        skier.setSubscription(new Subscription());
        skier.setPistes(new HashSet<>());
        skier.setRegistrations(new HashSet<>());
        return skier;
    }

    private Skier convertToEntity(SkierDTO skierDTO) {
        Skier skier = new Skier();
        // Convert SkierDTO fields to Skier fields
        return skier;
    }

}
