package triplan.back.dto;

public class ProfilForm {
    private String environnement;
    private String activite;
    private String budget;
    private String duree;
    private String experience;
    private String saison;
    private String compagnie;
    private String confort;

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
}