package tn.esprit.spring.SubscriptionTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SubscriptionServicesJUnitTest {

    @InjectMocks
    private SubscriptionServicesImpl subscriptionServices;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        List<Subscription> result = subscriptionServices.retrieveAllSubscriptions();
        Assertions.assertEquals(subscriptions, result);
    }

   /* @Test
    public void testAddSubscriptions() {
        Subscription subscription = new Subscription();
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        Subscription result = subscriptionServices.addSubscription(subscription);
        Assertions.assertEquals(subscription, result);
    }*/

    @Test
    public void testUpdateSubscription() {
        Subscription subscription = new Subscription();
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        Subscription result = subscriptionServices.updateSubscription(subscription);
        Assertions.assertEquals(subscription, result);
    }

    @Test
    public void testRetrieveSubscription() {
        Long subscriptionId = 1L;
        Subscription subscription = new Subscription();
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(java.util.Optional.of(subscription));
        Subscription result = subscriptionServices.retrieveSubscriptionById(subscriptionId);
        Assertions.assertEquals(subscription, result);
    }
}