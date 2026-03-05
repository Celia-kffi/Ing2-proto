package triplan.back.entities;
import javax.persistence.*;

@Entity
@Table(name = "hebergement_energie")
public class HebergementEnergie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "hebergement_id", nullable = false, unique = true)
    private Hebergements hebergement;

    private Double surfaceM2;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_chauffage")
    private TypeChauffage typeChauffage;

    @Column(name = "conso_base_kwh_m2_jour")
    private Double consoBaseKwhM2Jour;

    @Column(name = "conso_hotel_kwh_nuit")
    private Double consoHotelKwhNuit;

    private Boolean cuisine;
    private Boolean piscine;
    private Boolean baignoire;
    public HebergementEnergie() {}
    public Long getId() { return id; }

    public Hebergements getHebergement() { return hebergement; }
    public void setHebergement(Hebergements hebergement) { this.hebergement = hebergement; }

    public Double getSurfaceM2() { return surfaceM2; }
    public void setSurfaceM2(Double surfaceM2) { this.surfaceM2 = surfaceM2; }

    public TypeChauffage getTypeChauffage() { return typeChauffage; }
    public void setTypeChauffage(TypeChauffage typeChauffage) { this.typeChauffage = typeChauffage; }

    public Double getConsoBaseKwhM2Jour() { return consoBaseKwhM2Jour; }
    public void setConsoBaseKwhM2Jour(Double consoBaseKwhM2Jour) { this.consoBaseKwhM2Jour = consoBaseKwhM2Jour; }

    public Double getConsoHotelKwhNuit() { return consoHotelKwhNuit; }
    public void setConsoHotelKwhNuit(Double consoHotelKwhNuit) { this.consoHotelKwhNuit = consoHotelKwhNuit; }

    public Boolean getCuisine() { return cuisine; }
    public void setCuisine(Boolean cuisine) { this.cuisine = cuisine; }

    public Boolean getPiscine() { return piscine; }
    public void setPiscine(Boolean piscine) { this.piscine = piscine; }

    public Boolean getBaignoire() { return baignoire; }
    public void setBaignoire(Boolean baignoire) { this.baignoire = baignoire; }
}