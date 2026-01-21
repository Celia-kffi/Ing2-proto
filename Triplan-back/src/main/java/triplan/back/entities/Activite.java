package triplan.back.entities;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Table(name = "activites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "duree_visite_minutes", nullable = false)
    private Integer dureeVisiteMinutes;

    @Column(name = "type_activite", length = 100)
    private String typeActivite;

}
