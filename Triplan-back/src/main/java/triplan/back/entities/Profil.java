package triplan.back.entities;

import javax.persistence.*;

@Entity
@Table
public class Profil {
    @Id
    @SequenceGenerator(
            name="profil_sequence",
            sequenceName = "profil_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "profil_sequence"
    )
    private Long id;

    private String nom;
    private Integer budget;
    private Integer dureeMoyenne;
    private String typeExperience;

    public Profil() {
    }

    public Profil(Long id,
                  String nom,
                  Integer budget,
                  Integer dureeMoyenne,
                  String typeExperience) {
        this.id = id;
        this.nom = nom;
        this.budget = budget;
        this.dureeMoyenne = dureeMoyenne;
        this.typeExperience = typeExperience;
    }

    public Profil(String nom,
                  Integer budget,
                  Integer dureeMoyenne,
                  String typeExperience) {
        this.nom = nom;
        this.budget = budget;
        this.dureeMoyenne = dureeMoyenne;
        this.typeExperience = typeExperience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getDureeMoyenne() {
        return dureeMoyenne;
    }

    public void setDureeMoyenne(Integer dureeMoyenne) {
        this.dureeMoyenne = dureeMoyenne;
    }

    public String getTypeExperience() {
        return typeExperience;
    }

    public void setTypeExperience(String typeExperience) {
        this.typeExperience = typeExperience;
    }

    @Override
    public String toString() {
        return "Profil{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", budget=" + budget +
                ", dureeMoyenne=" + dureeMoyenne +
                ", typeExperience='" + typeExperience + '\'' +
                '}';
    }
}

