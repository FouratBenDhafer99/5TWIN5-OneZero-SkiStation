package tn.esprit.spring.dtow;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.dto.CourseDTO;

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