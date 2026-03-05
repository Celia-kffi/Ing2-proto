package triplan.back.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "empreinte_hebergement")
public class EmpreinteHebergement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valeurKgCO2;

    private int duree;

    @Enumerated(EnumType.STRING)
    private Saison saison;

    private boolean chauffageActive;

    private LocalDateTime dateCalcul;

    @ManyToOne
    @JoinColumn(name = "hebergement_id")
    private Hebergements hebergement;

    public EmpreinteHebergement() {
        this.dateCalcul = LocalDateTime.now();
    }

}