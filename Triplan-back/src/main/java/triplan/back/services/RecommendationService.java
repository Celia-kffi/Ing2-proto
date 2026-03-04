package triplan.back.services;

import org.springframework.stereotype.Service;
import triplan.back.dto.ProfilForm;
import triplan.back.entities.Voyage;
import triplan.back.repositories.VoyageRepository;

import java.util.*;

@Service
public class RecommendationService {

    private static final int TOTAL_RECO = 6;

    private final VoyageRepository voyageRepository;

    public RecommendationService(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    public List<Voyage> recommander(ProfilForm profil) {

        List<Voyage> voyages = voyageRepository.findAll();
        String themeDominant = determinerTheme(profil);

        voyages.sort((v1, v2) ->
                Integer.compare(
                        calculerScore(v2, profil, themeDominant),
                        calculerScore(v1, profil, themeDominant)
                )
        );

        return voyages.stream()
                .limit(TOTAL_RECO)
                .toList();
    }

    private String determinerTheme(ProfilForm p) {

        Map<String, Integer> scores = new HashMap<>();
        scores.put("luxe", 0);
        scores.put("aventure", 0);
        scores.put("culture", 0);
        scores.put("detente", 0);
        scores.put("nature", 0);

        if ("Montagne".equals(p.getEnvironnement())) {
            scores.merge("aventure", 2, Integer::sum);
            scores.merge("nature", 2, Integer::sum);
        } else if ("Mer".equals(p.getEnvironnement())) {
            scores.merge("detente", 2, Integer::sum);
            scores.merge("luxe", 1, Integer::sum);
        } else if ("Ville".equals(p.getEnvironnement())) {
            scores.merge("culture", 2, Integer::sum);
            scores.merge("luxe", 1, Integer::sum);
        }

        if ("Sports".equals(p.getActivite())) {
            scores.merge("aventure", 3, Integer::sum);
        } else if ("Musées".equals(p.getActivite())) {
            scores.merge("culture", 3, Integer::sum);
        } else if ("Spa".equals(p.getActivite())) {
            scores.merge("detente", 2, Integer::sum);
            scores.merge("luxe", 1, Integer::sum);
        }

        if ("Gros".equals(p.getBudget())) {
            scores.merge("luxe", 3, Integer::sum);
        } else if ("Moyen".equals(p.getBudget())) {
            scores.merge("culture", 1, Integer::sum);
            scores.merge("detente", 1, Integer::sum);
        } else if ("Petit".equals(p.getBudget())) {
            scores.merge("nature", 2, Integer::sum);
            scores.merge("aventure", 1, Integer::sum);
        }

        if ("Aventure".equals(p.getExperience())) {
            scores.merge("aventure", 3, Integer::sum);
            scores.merge("nature", 1, Integer::sum);
        } else if ("Relaxation".equals(p.getExperience())) {
            scores.merge("detente", 3, Integer::sum);
            scores.merge("luxe", 1, Integer::sum);
        } else if ("Culture".equals(p.getExperience())) {
            scores.merge("culture", 3, Integer::sum);
        }

        if ("Luxe".equals(p.getConfort())) {
            scores.merge("luxe", 2, Integer::sum);
        } else if ("Simple".equals(p.getConfort())) {
            scores.merge("nature", 2, Integer::sum);
            scores.merge("aventure", 1, Integer::sum);
        }

        return scores.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("culture");
    }

    private int calculerScore(Voyage v, ProfilForm p, String themeDominant) {

        int score = 0;

        if (v.getThemePrincipal() != null &&
                v.getThemePrincipal().toLowerCase().contains(themeDominant)) {
            score += 40;
        }

        if (v.getTheme() != null &&
                v.getTheme().toLowerCase().contains(themeDominant)) {
            score += 20;
        }

        if (v.getCategorie() != null && p.getExperience() != null) {
            String cat = v.getCategorie().toLowerCase();
            String exp = p.getExperience().toLowerCase();
            if (cat.contains(exp) || exp.contains(cat)) {
                score += 20;
            }
        }

        if (v.getBudgetMoyen() != null) {
            int bm = v.getBudgetMoyen();
            String b = p.getBudget();

            if ("Petit".equals(b) && bm <= 500) score += 20;
            if ("Moyen".equals(b) && bm > 500 && bm <= 1500) score += 20;
            if ("Gros".equals(b) && bm > 1500) score += 20;
        }

        return score;
    }
}