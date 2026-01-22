package triplan.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItineraryResponse {
    private List<ActiviteEtape> itineraire;
    private double distanceTotaleKm;
    private int dureeTotaleMinutes;
    private String algorithmeUtilise;
}