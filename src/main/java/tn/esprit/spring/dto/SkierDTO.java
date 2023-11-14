package tn.esprit.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Subscription;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SkierDTO {

    Long numSkier;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String city;

    Subscription subscription;

    Set<Piste> pistes;


    Set<Registration> registrations;
}
