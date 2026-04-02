package triplan.back.dto;

public class ProfilForm {

    // Formulaire
    private String environnement;   // Montagne, Mer, Ville, Nature
    private String activite;        // Sport, Culture, BienEtre, Gastronomie
    private String budget;          // Petit, Moyen, Confortable, Luxe
    private String duree;           // Weekend, Semaine, Long
    private String experience;      // Frisson, Serenite, Depaysement, Confort
    private String saison;          // Ete, Hiver, Printemps, Automne
    private String compagnie;       // Seul, Couple, Famille, Amis
    private String confort;         // Basique, Classique, Boutique, Palace
    private String rythme;          // Intense, Equilibre, Slow
    private String dateDebut;
    private String dateFin;


    private String typeExperience;
    private Integer budgetProfil;
    private Integer dureeMoyenne;

    public String getEnvironnement() { return environnement; }
    public void setEnvironnement(String v) { this.environnement = v; }

    public String getActivite() { return activite; }
    public void setActivite(String v) { this.activite = v; }

    public String getBudget() { return budget; }
    public void setBudget(String v) { this.budget = v; }

    public String getDuree() { return duree; }
    public void setDuree(String v) { this.duree = v; }

    public String getExperience() { return experience; }
    public void setExperience(String v) { this.experience = v; }

    public String getSaison() { return saison; }
    public void setSaison(String v) { this.saison = v; }

    public String getCompagnie() { return compagnie; }
    public void setCompagnie(String v) { this.compagnie = v; }

    public String getConfort() { return confort; }
    public void setConfort(String v) { this.confort = v; }

    public String getRythme() { return rythme; }
    public void setRythme(String v) { this.rythme = v; }

    public String getDateDebut() { return dateDebut; }
    public void setDateDebut(String v) { this.dateDebut = v; }

    public String getDateFin() { return dateFin; }
    public void setDateFin(String v) { this.dateFin = v; }

    public String getTypeExperience() { return typeExperience; }
    public void setTypeExperience(String v) { this.typeExperience = v; }

    public Integer getBudgetProfil() { return budgetProfil; }
    public void setBudgetProfil(Integer v) { this.budgetProfil = v; }

    public Integer getDureeMoyenne() { return dureeMoyenne; }
    public void setDureeMoyenne(Integer v) { this.dureeMoyenne = v; }
}