package triplan.back.entities;

import javax.persistence.*;
@Entity
public class FacteurActivite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacteur;

    private String typeActivite;
    private String unite;
    private double facteurEmission;
    public Long getIdFacteur() {
        return idFacteur;
    }

    public String getTypeActivite() {
        return typeActivite;
    }

    public String getUnite() {
        return unite;
    }

    public double getFacteurEmission() {
        return facteurEmission;
    }

    public void setIdFacteur(Long idFacteur) {
        this.idFacteur = idFacteur;
    }

    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setFacteurEmission(double facteurEmission) {
        this.facteurEmission = facteurEmission;
    }
}