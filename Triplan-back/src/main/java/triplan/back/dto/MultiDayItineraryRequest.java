package triplan.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MultiItineraryRequest {
    private List <Long> activiteIds;
    private long pointDepartIds;
    private int nbrJours;
    private int dureeMaxParJourMinutes = 480; //8h


}
