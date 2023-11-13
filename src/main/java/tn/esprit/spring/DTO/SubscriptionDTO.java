package tn.esprit.spring.DTO;
import java.time.LocalDate;
import lombok.*;
import tn.esprit.spring.entities.*;

@AllArgsConstructor 
@Getter 
@Setter 
@NoArgsConstructor
public class SubscriptionDTO {
    private Long NumSub;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float price;
    private TypeSubscription typeSub;
}
