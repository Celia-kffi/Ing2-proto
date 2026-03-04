package triplan.back.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class MutltiDayItineraryResponse {
    private int nbJours;
    private List<JourItineraire> jours;
    private double distanceTotalKm;
    private int dureeTotalMinutes;


}
