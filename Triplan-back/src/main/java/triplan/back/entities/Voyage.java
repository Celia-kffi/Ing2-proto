package triplan.back.entities;

import javax.persistence.*;

@Entity
@Table
public class Voyage {

    @Id
    @SequenceGenerator(
            name = "voyage_sequence",
            sequenceName = "voyage_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "voyage_sequence"
    )
    private Long id;

    private String nom;
    private String theme;
    private String pays;
    private Integer prix;
    private Integer budgetMoyen;
    private String destination;
    private String themePrincipal;

    public Voyage() {
    }

    public Voyage(Long id,
                  String nom,
                  String theme,
                  String pays,
                  Integer prix,
                  Integer budgetMoyen,
                  String destination,
                  String themePrincipal) {
        this.id = id;
        this.nom = nom;
        this.theme = theme;
        this.pays = pays;
        this.prix = prix;
        this.budgetMoyen = budgetMoyen;
        this.destination = destination;
        this.themePrincipal = themePrincipal;
    }

    public Voyage(String nom,
                  String theme,
                  String pays,
                  Integer prix,
                  Integer budgetMoyen,
                  String destination,
                  String themePrincipal) {
        this.nom = nom;
        this.theme = theme;
        this.pays = pays;
        this.prix = prix;
        this.budgetMoyen = budgetMoyen;
        this.destination = destination;
        this.themePrincipal = themePrincipal;
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getBudgetMoyen() {
        return budgetMoyen;
    }

    public void setBudgetMoyen(Integer budgetMoyen) {
        this.budgetMoyen = budgetMoyen;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getThemePrincipal() {
        return themePrincipal;
    }

    public void setThemePrincipal(String themePrincipal) {
        this.themePrincipal = themePrincipal;
    }

    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", theme='" + theme + '\'' +
                ", pays='" + pays + '\'' +
                ", prix=" + prix +
                ", budgetMoyen=" + budgetMoyen +
                ", destination='" + destination + '\'' +
                ", themePrincipal='" + themePrincipal + '\'' +
                '}';
    }
}
