package triplan.back.entities;
import javax.persistence.*;

@Entity
@Table(name = "coefficient_remplissage")
public class CoefficientRemplissage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "transport_id", nullable = false)
    private MoyenTransport transport;

    @Column(name = "niveau_remplissage", nullable = false)
    private String niveauRemplissage;

    @Column(nullable = false)
    private double coefficient;

    public CoefficientRemplissage() {}
    public CoefficientRemplissage(MoyenTransport transport, String niveauRemplissage, double coefficient) {
        this.transport = transport;
        this.niveauRemplissage = niveauRemplissage;
        this.coefficient = coefficient;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public MoyenTransport getTransport() { return transport; }
    public void setTransport(MoyenTransport transport) { this.transport = transport; }

    public String getNiveauRemplissage() { return niveauRemplissage; }
    public void setNiveauRemplissage(String niveauRemplissage) { this.niveauRemplissage = niveauRemplissage; }

    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
}

