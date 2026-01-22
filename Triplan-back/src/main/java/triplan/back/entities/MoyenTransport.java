package triplan.back.entities;
import javax.persistence.*;

@Entity
@Table(name = "moyen_transport")
public class MoyenTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(name = "facteur_emission", nullable = false)
    private double facteurEmission;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getFacteurEmission() { return facteurEmission; }
    public void setFacteurEmission(double facteurEmission) { this.facteurEmission = facteurEmission; }
}
