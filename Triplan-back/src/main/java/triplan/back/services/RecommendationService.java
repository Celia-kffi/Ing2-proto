package triplan.back.services;

import org.springframework.stereotype.Service;
import triplan.back.entities.Profil;
import triplan.back.entities.ProfilThemeScore;
import triplan.back.entities.Voyage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private static final int TOTAL_RECO = 6;

    private final VoyageService voyageService;

    public RecommendationService(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    public List<Voyage> recommend(
            Profil profil,
            List<ProfilThemeScore> themeScores,
            String pays
    ) {

        // regarder si la table des themes est null ou pas
        //elle marche (la liste n'est pas vide)
        if (themeScores == null || themeScores.isEmpty()) {
            return List.of();
        }


        // Map <theme, pourcentage> : structure la plus adapté a mon cas
        //elle assosie chaque theme unique a une valeur
        //on recupère de la table profil_theme_score et on stocke dans cette structure
        Map<String, Integer> preferences = new LinkedHashMap<>();

        for (ProfilThemeScore pts : themeScores) {
            if (pts.getTheme() != null) {
                String theme = pts.getTheme().toLowerCase();
                Integer pourcentage = pts.getPourcentage();
                preferences.put(theme, pourcentage);
            }
        }

        // Calcul de la somme des pourcentages
        int somme = 0;
        for (int pourcentage : preferences.values()) {
            somme += pourcentage;
        }

        if (somme == 0) {
            return List.of();
        }


        //calcule des proportions (devise sur la somme)

        Map<String, Double> proportions = new LinkedHashMap<>();

        for (String theme : preferences.keySet()) {

            int pourcentage = preferences.get(theme); //récupére la valeur de chaque clé

            double proportion = (double) pourcentage / somme;

            proportions.put(theme, proportion);
        }


        /* ==============================
           3. Méthode de Hamilton
           ============================== */

        Map<String, Integer> quotas = new LinkedHashMap<>();
        Map<String, Double> restes = new LinkedHashMap<>();

        int attribue = 0;

        // calcul des parts entières
        for (String theme : proportions.keySet()) {

            double theorique = proportions.get(theme) * TOTAL_RECO;
            //on prend la partie entière
            int entier = (int) theorique;

            quotas.put(theme, entier);
            restes.put(theme, theorique - entier);

            attribue += entier;
        }

        //mntn on passe aux restes
        while (attribue < TOTAL_RECO) {

            String meilleurTheme = null;
            double plusGrandReste = -1;

            // On cherche le thème avec le plus grand reste
            for (String theme : restes.keySet()) {
                if (restes.get(theme) > plusGrandReste) {
                    plusGrandReste = restes.get(theme);
                    meilleurTheme = theme;
                }
            }

            // on lui donne un voyage en plus
            quotas.put(meilleurTheme, quotas.get(meilleurTheme) + 1);
            restes.put(meilleurTheme, 0.0);

            attribue++;
        }





// 1. Récupération de tous les voyages
        List<Voyage> tousLesVoyages = voyageService.getVoyages();

// 2. Regroupement des voyages par thème
        Map<String, List<Voyage>> parTheme = new LinkedHashMap<>();

        for (Voyage v : tousLesVoyages) {

            if (v.getTheme() == null) {
                continue;
            }

            String theme = v.getTheme().toLowerCase().trim();

            if (!parTheme.containsKey(theme)) {
                parTheme.put(theme, new ArrayList<>());
            }

            parTheme.get(theme).add(v);
        }

// 3. Sélection des voyages selon les quotas
        List<Voyage> resultat = new ArrayList<>();
        int manque = 0;

        for (String theme : quotas.keySet()) {

            List<Voyage> dispo = parTheme.get(theme);

            if (dispo == null) {
                manque += quotas.get(theme);
                continue;
            }

            int pris = Math.min(quotas.get(theme), dispo.size());

            for (int i = 0; i < pris; i++) {
                resultat.add(dispo.get(i));
            }

            manque += quotas.get(theme) - pris;
        }


        /* ==============================
           5. Redistribution si manque
           ============================== */
//        Map<String, Double> restesTemp = new LinkedHashMap<>(restes);
//
//
//        if (manque > 0) {
//
//            for (String theme : ordreRestes) {
//
//                if (manque == 0) {
//                    break;
//                }
//
//                List<Voyage> dispo = parTheme.get(theme);
//
//                if (dispo == null) {
//                    continue;
//                }
//
//                // Compter combien de voyages de ce thème ont déjà été pris
//                int dejaPris = 0;
//                for (Voyage v : resultat) {
//                    if (v.getTheme() != null &&
//                            v.getTheme().equalsIgnoreCase(theme)) {
//                        dejaPris++;
//                    }
//                }
//
//                // S'il reste des voyages disponibles pour ce thème
//                if (dejaPris < dispo.size()) {
//                    resultat.add(dispo.get(dejaPris));
//                    manque--;
//                }
//            }
//        }

        /* ==============================
           6. Sécurité finale
           ============================== */

        // Si aucun résultat (cas extrême), on renvoie des voyages par défaut
        if (resultat.isEmpty()) {
            return Collections.emptyList();
        }

        return resultat;
    }
}
