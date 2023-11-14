package tn.esprit.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Skier;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RegistrationDTO {

    Long numRegistration;
    int numWeek;
    Skier skier;
    Course course;
}