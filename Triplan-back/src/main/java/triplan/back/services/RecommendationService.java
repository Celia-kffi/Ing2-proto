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

    public List<Voyage> recommander(ProfilForm p) {
        List<Voyage> voyages = voyageRepository.findAll();

        voyages.sort((v1, v2) ->
                calculerScore(v2, p) - calculerScore(v1, p)
        );

        return voyages.stream().limit(TOTAL_RECO).toList();
    }

    private int calculerScore(Voyage v, ProfilForm p) {
        int score = 0;

        if (v.getTheme() != null && p.getTypeExperience() != null) {
            String theme = v.getTheme().toLowerCase();
            String exp = p.getTypeExperience().toLowerCase();

            if (theme.equals(exp)) score = score+ 15;
            else if (proximiteTheme(theme, exp)) score =score + 7;
            else if (themeIncompatible(theme, exp)) score =score - 8;
        }

        if (v.getPrix() != null && p.getBudgetProfil() != null) {
            int diff = Math.abs(v.getPrix() - p.getBudgetProfil());

            if (diff <= 150) score += 12;
            else if (diff <= 400) score += 8;
            else if (diff <= 800) score += 4;
            else if (diff > 1500) score -= 6;
        }

        if (p.getDureeMoyenne() != null && p.getDuree() != null) {
            int dm = p.getDureeMoyenne();

            boolean ok =
                    (dm <= 3 && "Weekend".equals(p.getDuree())) ||
                            (dm <= 9 && "Semaine".equals(p.getDuree())) ||
                            (dm > 9 && "Long".equals(p.getDuree()));

            if (ok) score =score + 8;
        }

        if (p.getExperience() != null && v.getTheme() != null) {
            String theme = v.getTheme().toLowerCase();
            String cat = v.getCategorie() != null ? v.getCategorie().toLowerCase() : "";

            switch (p.getExperience()) {
                case "Frisson" -> {
                    if (theme.equals("aventure")) score += 20;
                    else if (cat.matches("ski|surf|rafting|canyoning|plongee|alpinisme|trek")) score += 15;
                    else if (theme.equals("luxe") || theme.equals("detente")) score -= 5;
                }
                case "Serenite" -> {
                    if (theme.equals("detente")) score += 20;
                    else if (cat.matches("spa|villa_privee|chalet.*")) score += 15;
                    else if (theme.equals("aventure")) score -= 5;
                }
                case "Depaysement" -> {
                    if (cat.matches("trek|alpinisme|canyoning|plongee|croisiere.*|yacht")) score += 20;
                    else if (theme.equals("aventure") || theme.equals("luxe")) score += 10;
                }
                case "Confort" -> {
                    if (theme.equals("luxe")) score += 20;
                    else if (cat.matches("hotel_5.*|palace|villa_privee|ski_premium|experience_vip")) score += 15;
                    else if (theme.equals("aventure")) score -= 8;
                }
            }
        }

        if (p.getEnvironnement() != null && v.getThemePrincipal() != null) {
            String tp = v.getThemePrincipal().toLowerCase();
            String env = p.getEnvironnement().toLowerCase();

            if (tp.equals(env)) score += 18;
            else if (proximiteEnvironnement(tp, env)) score += 8;
            else score -= 4;
        }

        if (p.getActivite() != null && v.getCategorie() != null) {
            String cat = v.getCategorie().toLowerCase();

            switch (p.getActivite()) {
                case "Sport" -> {
                    if (cat.matches("ski|ski_premium|surf|rafting|canyoning|plongee|alpinisme|trek")) score += 17;
                    else if (cat.matches("yacht|croisiere.*")) score += 5;
                    else if (cat.matches("spa|musee|gastronomie|patrimoine|festival")) score -= 6;
                }
                case "Culture" -> {
                    if (cat.matches("musee|patrimoine|festival|gastronomie|route_des_vins")) score += 17;
                    else if (cat.matches("experience_vip|ville")) score += 8;
                    else if (cat.matches("ski|surf|rafting|canyoning")) score -= 6;
                }
                case "BienEtre" -> {
                    if (cat.matches("spa|villa_privee|chalet.*|hotel_5.*")) score += 17;
                    else if (cat.matches("yacht|croisiere.*")) score += 10;
                    else if (cat.matches("ski|rafting|canyoning|alpinisme")) score -= 6;
                }
                case "Gastronomie" -> {
                    if (cat.matches("gastronomie|route_des_vins|festival")) score += 17;
                    else if (cat.matches("experience_vip|hotel_5.*|villa_privee")) score += 10;
                    else if (cat.matches("trek|alpinisme|camping.*")) score -= 4;
                }
            }
        }

        if (p.getBudget() != null && v.getPrix() != null) {
            int px = v.getPrix();

            switch (p.getBudget()) {
                case "Petit" -> {
                    if (px <= 600) score += 15;
                    else if (px <= 900) score += 6;
                    else if (px > 1500) score -= 10;
                    else score -= 4;
                }
                case "Moyen" -> {
                    if (px > 600 && px <= 1200) score += 15;
                    else if (px <= 600) score += 8;
                    else if (px <= 1800) score += 6;
                    else score -= 8;
                }
                case "Confortable" -> {
                    if (px > 1200 && px <= 2000) score += 15;
                    else if (px > 800) score += 7;
                    else score += 3;
                }
                case "Luxe" -> {
                    if (px > 2000) score += 15;
                    else if (px > 1500) score += 10;
                    else if (px < 800) score -= 6;
                }
            }
        }

        if (p.getConfort() != null && v.getCategorie() != null) {
            String cat = v.getCategorie().toLowerCase();

            switch (p.getConfort()) {
                case "Basique" -> {
                    if (cat.matches("ski|trek|surf|rafting|canyoning|alpinisme|plongee")) score += 12;
                    else if (cat.matches("musee|patrimoine|festival")) score += 6;
                    else if (cat.matches("yacht|hotel_5.*|villa_privee|chalet.*|experience_vip")) score -= 8;
                }
                case "Classique" -> {
                    if (cat.matches("musee|patrimoine|gastronomie|festival|spa|route_des_vins")) score += 12;
                    else if (cat.matches("ski|trek|surf")) score += 5;
                    else if (cat.matches("yacht|villa_privee")) score -= 4;
                }
                case "Boutique" -> {
                    if (cat.matches("spa|chalet.*|villa_privee|gastronomie|route_des_vins|experience_vip")) score += 12;
                    else if (cat.matches("hotel_5.*|ski_premium")) score += 8;
                    else if (cat.matches("trek|surf|rafting")) score -= 4;
                }
                case "Palace" -> {
                    if (cat.matches("yacht|hotel_5.*|villa_privee|chalet_premium|ski_premium|croisiere_luxe|experience_vip")) score += 12;
                    else if (cat.matches("spa|gastronomie")) score += 6;
                    else if (cat.matches("trek|surf|rafting|canyoning|alpinisme")) score -= 10;
                }
            }
        }

        if (p.getCompagnie() != null && v.getCategorie() != null) {
            String cat = v.getCategorie().toLowerCase();

            switch (p.getCompagnie()) {
                case "Seul" -> { if (cat.matches("trek|alpinisme|surf|musee|patrimoine|festival|canyoning|rafting")) score += 8; }
                case "Couple" -> { if (cat.matches("yacht|villa_privee|spa|croisiere.*|chalet.*|hotel_5.*|gastronomie|route_des_vins")) score += 8; }
                case "Famille" -> { if (cat.matches("ski.*|plongee|festival|musee|patrimoine|gastronomie")) score += 8; }
                case "Amis" -> { if (cat.matches("ski.*|surf|rafting|canyoning|plongee|festival|route_des_vins")) score += 8; }
            }
        }

        if (p.getRythme() != null && v.getTheme() != null) {
            String theme = v.getTheme().toLowerCase();
            String cat = v.getCategorie() != null ? v.getCategorie().toLowerCase() : "";

            switch (p.getRythme()) {
                case "Intense" -> { if (theme.equals("aventure") || cat.matches("ski.*|trek|alpinisme|surf|rafting|canyoning")) score += 6; }
                case "Equilibre" -> { if (theme.equals("culture") || cat.matches("gastronomie|festival|patrimoine|musee|route_des_vins")) score += 6; }
                case "Slow" -> { if (theme.equals("detente") || theme.equals("luxe") || cat.matches("spa|villa.*|yacht|chalet.*")) score += 6; }
            }
        }

        return score;
    }

    private boolean proximiteTheme(String theme, String expProfil) {
        return switch (expProfil) {
            case "luxe" -> theme.equals("detente");
            case "detente" -> theme.equals("luxe") || theme.equals("culture");
            case "aventure" -> theme.equals("culture");
            case "culture" -> theme.equals("detente") || theme.equals("aventure");
            default -> false;
        };
    }

    private boolean themeIncompatible(String theme, String expProfil) {
        return switch (expProfil) {
            case "luxe" -> theme.equals("aventure");
            case "aventure" -> theme.equals("luxe") || theme.equals("detente");
            default -> false;
        };
    }

    private boolean proximiteEnvironnement(String tp, String env) {
        return switch (env) {
            case "montagne" -> tp.equals("nature");
            case "nature" -> tp.equals("montagne");
            case "mer" -> tp.equals("nature");
            default -> false;
        };
    }
}