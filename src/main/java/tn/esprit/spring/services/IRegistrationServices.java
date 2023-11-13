package tn.esprit.spring.services;

import tn.esprit.spring.entities.*;

import java.time.LocalDate;
import java.util.List;

public interface IRegistrationServices {

	Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier);
	Registration assignRegistrationToCourse(Long numRegistration, Long numCourse);
	Registration addRegistrationAndAssignToSkierAndCourse(Registration registration, Long numSkieur, Long numCours);
	List<Integer> numWeeksCourseOfInstructorBySupport(Long numInstructor, Support support);
	List<Subscription> retrieveSubscriptionsByDates(LocalDate startDate, LocalDate endDate);

}
