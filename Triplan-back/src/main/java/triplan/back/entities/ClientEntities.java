package triplan.back.entities;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class ClientEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prenom;
    private String nom;
    private int age;
    private String nationalite;
    private String voyage;

    @Column(name = "en_famille")
    private boolean enFamille;

    public ClientEntities() {}

    public ClientEntities(String prenom, String nom, int age,
                          String nationalite, String voyage,
                          boolean enFamille) {
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
        this.nationalite = nationalite;
        this.voyage = voyage;
        this.enFamille = enFamille;
    }

    public Long getId() { return id; }
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public int getAge() { return age; }
    public String getNationalite() { return nationalite; }
    public String getVoyage() { return voyage; }
    public boolean isEnFamille() { return enFamille; }
}