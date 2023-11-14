package tn.esprit.spring.dtow;



import lombok.Getter;
import lombok.NoArgsConstructor;
import tn.esprit.spring.dto.CourseDTO;

import java.time.LocalDate;
import java.util.Set;


@Getter
@NoArgsConstructor
public class InstructorDTO {
    Long numInstructor;
    String firstName;
    String lastName;
    LocalDate dateOfHire;
    Set<CourseDTO> courses;
}
