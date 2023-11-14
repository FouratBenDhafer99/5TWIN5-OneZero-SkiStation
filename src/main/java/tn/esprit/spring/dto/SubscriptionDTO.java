package tn.esprit.spring.dto;

import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;

public class SubscriptionDTO {
    private Long numSub;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float price;
    private TypeSubscription typeSub;

}
