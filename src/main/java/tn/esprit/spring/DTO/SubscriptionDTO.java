package tn.esprit.spring.DTO;

import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;

public class SubscriptionDTO {
    private Long NumSub;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float price;
    private TypeSubscription typeSub;

    public Long getNumSub() {
        return NumSub;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public TypeSubscription getTypeSub() {
        return typeSub;
    }

    public void setTypeSub(TypeSubscription typeSub) {
        this.typeSub = typeSub;
    }
}
