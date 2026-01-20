package triplan.back.entities;

import javax.persistence.*;
@Entity
@Table(name = "coefficient_infrastructure")
public class CoefficientInfrastructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transport_id", nullable = false)
    private MoyenTransport transport;

    @Column(name = "type_infrastructure", nullable = false)
    private String typeInfrastructure;

    @Column(nullable = false)
    private double coefficient;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MoyenTransport getTransport() { return transport; }
    public void setTransport(MoyenTransport transport) { this.transport = transport; }

    public String getTypeInfrastructure() { return typeInfrastructure; }
    public void setTypeInfrastructure(String typeInfrastructure) { this.typeInfrastructure = typeInfrastructure; }

    public double getCoefficient() { return coefficient; }
    public void setCoefficient(double coefficient) { this.coefficient = coefficient; }
}
