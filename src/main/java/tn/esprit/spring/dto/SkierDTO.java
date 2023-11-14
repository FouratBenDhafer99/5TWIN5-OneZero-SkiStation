package tn.esprit.spring.dto;

import lombok.*;
import tn.esprit.spring.entities.*;
import java.time.LocalDate;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class SkierDTO {
    private Long numSkier;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String city;
    private Subscription subscription;
    private Set<Piste> pistes;
    private Set<Registration> registrations;
}