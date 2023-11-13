package tn.esprit.spring;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.*;

import java.time.*;
import java.util.ArrayList;
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
    @Test
    public void testAddSkier() {
        Skier skier = new Skier();
        when(skierRepository.save(skier)).thenReturn(skier);

        Subscription sb = new Subscription(1L, LocalDate.now(), LocalDate.now().plusMonths(6), 99.99f, TypeSubscription.MONTHLY);
        skier.setSubscription(sb);


        Skier result = skierServices.addSkier(skier);

        assertEquals(skier, result);
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
