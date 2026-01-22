package triplan.back.entities;

import javax.persistence.*;

@Entity
@Table(name = "empreinte_carbone_trajet")
public class EmpreinteCarbone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "distance_km", nullable = false)
    private double distanceKm;

    @ManyToOne
    @JoinColumn(name = "transport_id", nullable = false)
    private MoyenTransport transport;

    public EmpreinteCarbone() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public MoyenTransport getTransport() {
        return transport;
    }

    public void setTransport(MoyenTransport transport) {
        this.transport = transport;
    }
}
