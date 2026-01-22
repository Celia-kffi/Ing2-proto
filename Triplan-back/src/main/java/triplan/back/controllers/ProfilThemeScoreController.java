package triplan.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import triplan.back.entities.ProfilThemeScore;
import triplan.back.services.ProfilThemeScoreService;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/profil-theme-score")
public class ProfilThemeScoreController {

    private final ProfilThemeScoreService profilThemeScoreService;

    @Autowired
    public ProfilThemeScoreController(ProfilThemeScoreService profilThemeScoreService) {
        this.profilThemeScoreService = profilThemeScoreService;
    }

    @GetMapping
    public List<ProfilThemeScore> getAllProfilThemeScores() {
        return profilThemeScoreService.getProfilThemeScores();
    }
}
