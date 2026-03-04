package triplan.back.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JourItineraire {
    private int numeroJour;
    private List<ActiviteEtape> activites;
    private int dureeTotaleMinutes;
    private double distanceTotaleKm;

}
