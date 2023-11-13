package tn.esprit.spring.DTO;
import tn.esprit.spring.entities.TypeSubscription;
import java.time.LocalDate;

@AllArgsConstructor @Getter @Setter @NoArgsConstructor
public class SubscriptionDTO {
    private Long NumSub;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float price;
    private TypeSubscription typeSub;
}
