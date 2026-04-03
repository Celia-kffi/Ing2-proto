package triplan.back.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "hebergements")
public class Hebergements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(name = "nb_etoiles")
    private Short nbEtoiles;

    @Column(nullable = false, length = 100)
    private String destination;

    @Column(nullable = false, length = 80)
    private String pays;

    @Column(length = 80)
    private String ville;

    @Column(name = "prix_nuit", nullable = false, precision = 8, scale = 2)
    private BigDecimal prixNuit;

    @Column(length = 5)
    private String devise = "EUR";

    @Column(name = "niveau_confort", length = 20)
    private String niveauConfort;

    @Column(name = "budget_cible", length = 20)
    private String budgetCible;

    @Column(name = "equipements", columnDefinition = "text")
    private String equipements;

    @Column(name = "empreinte_carbone" )
    private Double empreinteCarbone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Short getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(Short nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public BigDecimal getPrixNuit() {
        return prixNuit;
    }

    public void setPrixNuit(BigDecimal prixNuit) {
        this.prixNuit = prixNuit;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getNiveauConfort() {
        return niveauConfort;
    }

    public void setNiveauConfort(String niveauConfort) {
        this.niveauConfort = niveauConfort;
    }

    public String getBudgetCible() {
        return budgetCible;
    }

    public void setBudgetCible(String budgetCible) {
        this.budgetCible = budgetCible;
    }

    public String getEquipements() {
        return equipements;
    }

    public void setEquipements(String equipements) {
        this.equipements = equipements;
    }

    public void setEmpreinteCarbone(Double empreinteCarbone) {}

    public Double getEmpreinteCarbone() {return empreinteCarbone;}
}