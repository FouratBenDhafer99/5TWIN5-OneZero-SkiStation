package tn.esprit.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class InstructorDTO {
    Long numInstructor;
    String firstName;
    String lastName;
    LocalDate dateOfHire;
    Set<CourseDTO> courses;
}