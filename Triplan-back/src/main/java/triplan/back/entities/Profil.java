package triplan.back.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String prenom;

    @Column(name = "adresse_mail", unique = true)
    private String adresseMail;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    private Integer budget;

    @Column(name = "duree_moyenne")
    private Integer dureeMoyenne;

    @Column(name = "type_experience")
    private String typeExperience;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    private Boolean actif;
    private String role;

    public Profil() {
    }

    public Profil(Long id,
                  String nom,
                  String prenom,
                  String adresseMail,
                  String motDePasse,
                  Integer budget,
                  Integer dureeMoyenne,
                  String typeExperience,
                  LocalDateTime dateCreation,
                  Boolean actif,
                  String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.budget = budget;
        this.dureeMoyenne = dureeMoyenne;
        this.typeExperience = typeExperience;
        this.dateCreation = dateCreation;
        this.actif = actif;
        this.role = role;
    }

    public Profil(String nom,
                  String prenom,
                  String adresseMail,
                  String motDePasse,
                  Integer budget,
                  Integer dureeMoyenne,
                  String typeExperience) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.budget = budget;
        this.dureeMoyenne = dureeMoyenne;
        this.typeExperience = typeExperience;
        this.actif = true;
        this.role = "USER";
        this.dateCreation = LocalDateTime.now();
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Profil{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresseMail='" + adresseMail + '\'' +
                ", budget=" + budget +
                ", dureeMoyenne=" + dureeMoyenne +
                ", typeExperience='" + typeExperience + '\'' +
                ", actif=" + actif +
                ", role='" + role + '\'' +
                '}';
    }
}
