package tn.esprit.spring.dto;

import lombok.*;
import tn.esprit.spring.entities.*;

/**
 * @author Jozef
 */
@AllArgsConstructor @Getter @Setter @NoArgsConstructor
public class CourseDTO {
    private int level;
    private TypeCourse typeCourse;
    private Support support;
    private Float price;
    private int timeSlot;
}
