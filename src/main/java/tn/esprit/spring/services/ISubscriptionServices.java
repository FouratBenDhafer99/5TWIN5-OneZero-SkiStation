package tn.esprit.spring.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import tn.esprit.spring.dto.SubscriptionDTO;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;

public interface ISubscriptionServices {

	List<Subscription> retrieveAllSubscriptions();
	Subscription addSubscription(Subscription subscription);

	Subscription updateSubscription(Subscription subscription);

	Subscription retrieveSubscriptionById(Long numSubscription);

	Set<Subscription> getSubscriptionByType(TypeSubscription type);

	Subscription getSubscriptionById(Long id);
	SubscriptionDTO convertToDTO(Subscription subscription);

	List<Subscription> retrieveSubscriptionsByDates(LocalDate startDate, LocalDate endDate);

	void retrieveSubscriptions();
}