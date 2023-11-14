package tn.esprit.spring.SubscriptionTests;

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
import static org.mockito.Mockito.*;


public class SubscriptionServicesMockitoTest {

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription(1L, LocalDate.now(), LocalDate.now().plusMonths(6), 99.99f, TypeSubscription.MONTHLY));
        subscriptions.add(new Subscription(2L, LocalDate.now(), LocalDate.now().plusMonths(11), 199.99f, TypeSubscription.ANNUAL));
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        List<Subscription> result = subscriptionServices.retrieveAllSubscriptions();
        assertEquals(subscriptions, result);
    }

    @Test
    public void testAddSubscription() {
        Subscription subscription = new Subscription(3L, LocalDate.now(), LocalDate.now().plusMonths(12), 80.99f, TypeSubscription.SEMESTRIEL);
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        Subscription result = subscriptionServices.addSubscription(subscription);
        assertEquals(subscription, result);
    }

    @Test
    public void testUpdateSubscription() {
        Subscription existingSubscription = new Subscription(4L, LocalDate.now(), LocalDate.now().plusMonths(10), 80.99f, TypeSubscription.SEMESTRIEL);
        Subscription updatedSubscription = new Subscription(4L, LocalDate.now(), LocalDate.now().plusMonths(12), 80.99f, TypeSubscription.ANNUAL);
        when(subscriptionRepository.save(updatedSubscription)).thenReturn(updatedSubscription);
        when(subscriptionRepository.findById(existingSubscription.getNumSub())).thenReturn(Optional.of(existingSubscription));
        Subscription result = subscriptionServices.updateSubscription(updatedSubscription);
        assertEquals(updatedSubscription, result);
    }

    @Test
    public void testRetrieveSubscription() {
        Long subscriptionId = 5L;
        Subscription subscription = new Subscription(5L, LocalDate.now(), LocalDate.now().plusMonths(6), 80.99f, TypeSubscription.MONTHLY);
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));
        Subscription result = subscriptionServices.retrieveSubscriptionById(subscriptionId);
        assertEquals(subscription, result);
    }
}