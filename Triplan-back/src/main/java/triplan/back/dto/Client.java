package triplan.back.dto;

import java.util.List;

public class Client {

    private String prenom;
    private String nom;
    private int age;
    private String nationalite;
    private String voyage;
    private boolean enFamille;
    private List<String> membresFamille;

    public Client(String prenom, String nom, int age,
                         String nationalite, String voyage,
                         boolean enFamille) {
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
        this.nationalite = nationalite;
        this.voyage = voyage;
        this.enFamille = enFamille;
    }

    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public int getAge() { return age; }
    public String getNationalite() { return nationalite; }
    public String getVoyage() { return voyage; }
    public boolean isEnFamille() { return enFamille; }

}