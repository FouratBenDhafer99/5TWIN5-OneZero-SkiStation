package tn.esprit.spring.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
public class Subscription implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long numSub;
	LocalDate startDate;
	LocalDate endDate;
	Float price;
//	@Enumerated(EnumType.STRING)
	TypeSubscription typeSub;

	//////////////////GETTERS//////////////////
	public Long getNumSub() {
		return numSub;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Float getPrice() {
		return price;
	}

	public TypeSubscription getTypeSub() {
		return typeSub;
	}

	//////////////////SETTERS//////////////////

	public void setNumSub(Long numSub) {
		this.numSub = numSub;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setTypeSub(TypeSubscription typeSub) {
		this.typeSub = typeSub;
	}

	//////////////////CONSTRUCTORS//////////////////
	public Subscription(Long numSub, LocalDate startDate, LocalDate endDate, Float price, TypeSubscription typeSub) {
		this.numSub = numSub;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.typeSub = typeSub;
	}

	public Subscription() {
		this(0L, LocalDate.now(), LocalDate.now(), 0.0f, TypeSubscription.MONTHLY);
	}

}
