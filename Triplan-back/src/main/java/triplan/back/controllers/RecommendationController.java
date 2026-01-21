package triplan.back.controllers;

import org.springframework.web.bind.annotation.*;
import triplan.back.entities.Profil;
import triplan.back.entities.ProfilThemeScore;
import triplan.back.entities.Voyage;
import triplan.back.services.ProfilService;
import triplan.back.services.ProfilThemeScoreService;
import triplan.back.services.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "http://172.31.253.128:3000")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final ProfilService profilService;
    private final ProfilThemeScoreService profilThemeScoreService;

    public RecommendationController(
            RecommendationService recommendationService,
            ProfilService profilService,
            ProfilThemeScoreService profilThemeScoreService
    ) {
        this.recommendationService = recommendationService;
        this.profilService = profilService;
        this.profilThemeScoreService = profilThemeScoreService;
    }

    @GetMapping
    public List<Voyage> getRecommendations(
            @RequestParam Long profilId
    ) {


        Profil profil = profilService.getProfilById(profilId);
        if (profil == null) {
            return List.of();
        }


        List<ProfilThemeScore> themeScores =
                profilThemeScoreService.getByProfilId(profilId);


        return recommendationService.recommend(
                profil,
                themeScores,
                "France" // valeur fixe (tous les voyages sont en France)
        );
    }
}
