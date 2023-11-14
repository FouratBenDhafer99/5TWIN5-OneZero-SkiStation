package tn.esprit.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Skier;


import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PisteDTO {

    Long numPiste;
    String namePiste;
    Color color;
    int length;
    int slope;
    Set<Skier> skiers;
}
