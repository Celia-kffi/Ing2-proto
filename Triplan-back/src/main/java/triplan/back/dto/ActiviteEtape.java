package triplan.back.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor

public class ActiviteEtape {
    private int ordre;
    private Long activiteId;
    private String nom;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int dureeVisiteMinutes;
    private Double distanceDepuisPrecedenteKm;
    private Integer tempsTrajetDepuisPrecedentMinutes;
    private String typeActivite;


    public ActiviteEtape(int ordre, Long activiteId, String nom, BigDecimal latitude,
                         BigDecimal longitude, int dureeVisiteMinutes,
                         Double distanceDepuisPrecedenteKm, Integer tempsTrajetDepuisPrecedentMinutes,
                         String typeActivite) {
        this.ordre = ordre;
        this.activiteId = activiteId;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dureeVisiteMinutes = dureeVisiteMinutes;
        this.distanceDepuisPrecedenteKm = distanceDepuisPrecedenteKm;
        this.tempsTrajetDepuisPrecedentMinutes = tempsTrajetDepuisPrecedentMinutes;
        this.typeActivite = typeActivite;
    }
}
