package triplan.back.entities;

import javax.persistence.*;

@Entity
@Table
public class ProfilThemeScore {

    @Id
    @SequenceGenerator(
            name = "profil_theme_score_sequence",
            sequenceName = "profil_theme_score_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "profil_theme_score_sequence"
    )
    private Long id;

    private Long profilId;
    private String theme;
    private Integer pourcentage;
    private Integer score;

    public ProfilThemeScore() {
    }

    public ProfilThemeScore(Long id,
                            Long profilId,
                            String theme,
                            Integer pourcentage,
                            Integer score) {
        this.id = id;
        this.profilId = profilId;
        this.theme = theme;
        this.pourcentage = pourcentage;
        this.score = score;
    }

    public ProfilThemeScore(Long profilId,
                            String theme,
                            Integer pourcentage,
                            Integer score) {
        this.profilId = profilId;
        this.theme = theme;
        this.pourcentage = pourcentage;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfilId() {
        return profilId;
    }

    public void setProfilId(Long profilId) {
        this.profilId = profilId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Integer pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ProfilThemeScore{" +
                "id=" + id +
                ", profilId=" + profilId +
                ", theme='" + theme + '\'' +
                ", pourcentage=" + pourcentage +
                ", score=" + score +
                '}';
    }
}
