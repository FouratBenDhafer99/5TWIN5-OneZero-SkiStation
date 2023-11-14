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

	List<Subscription> retrieveSubscriptionsByDates(LocalDate startDate, LocalDate endDate);
	SubscriptionDTO convertToDTO(Subscription subscription);

	void retrieveSubscriptions();
}
