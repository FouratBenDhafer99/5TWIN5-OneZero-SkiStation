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

import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class SkierServicesJUnitTest {
    @InjectMocks
    private SkierServicesImpl skierServices;

    @Mock
    private ISkierRepository skierRepository;

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
    public void testAddSkier() {
        Skier skier = new Skier();
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setDateOfBirth(LocalDate.of(1990, 1, 1));
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL); // You can choose any type here
        skier.setSubscription(subscription);
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);
        Skier savedSkier = skierServices.addSkier(skier);
        verify(skierRepository, times(1)).save(skier);
        reset(skierRepository);
    }


    @Test
    public void testRetrieveSkier() {
        Long skierId = 1L;
        Skier skier = new Skier();
        when(skierRepository.findById(skierId)).thenReturn(java.util.Optional.of(skier));
        Skier result = skierServices.retrieveSkier(skierId);
        assertEquals(skier, result);
    }
}
