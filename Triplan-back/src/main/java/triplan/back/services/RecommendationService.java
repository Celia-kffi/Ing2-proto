package triplan.back.services;

import org.springframework.stereotype.Service;
import triplan.back.entities.Profil;
import triplan.back.entities.ProfilThemeScore;
import triplan.back.entities.Voyage;

import java.util.*;

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

        System.out.println("\n==============================");
        System.out.println("DEBUT RECOMMANDATION");
        System.out.println("Profil : " + profil.getNom());
        System.out.println("Pays : " + pays);
        System.out.println("==============================\n");

        // regarder si la table des themes est null ou pas
        //elle marche (la liste n'est pas vide)
        if (themeScores == null || themeScores.isEmpty()) {
            System.out.println("Aucun theme score");
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

        System.out.println("Preferences :");
        preferences.forEach((t, p) ->
                System.out.println(" - " + t + " : " + p + "%"));
        System.out.println();

        // Calcul de la somme des pourcentages
        int somme = 0;
        for (int pourcentage : preferences.values()) {
            somme += pourcentage;
        }

        System.out.println("Somme des pourcentages = " + somme);

        if (somme == 0) {
            System.out.println("Somme nulle");
            return List.of();
        }

        //calcule des proportions (devise sur la somme)
        Map<String, Double> proportions = new LinkedHashMap<>();

        for (String theme : preferences.keySet()) {
            int pourcentage = preferences.get(theme);
            double proportion = (double) pourcentage / somme;
            proportions.put(theme, proportion);
        }

        System.out.println("\nProportions :");
        proportions.forEach((t, p) ->
                System.out.printf(" - %s : %.3f%n", t, p));

        Map<String, Integer> quotas = new LinkedHashMap<>();
        Map<String, Double> restes = new LinkedHashMap<>();

        int attribue = 0;

        // calcul des parts entières
        System.out.println("\nQuotas théoriques :");
        for (String theme : proportions.keySet()) {
            double theorique = proportions.get(theme) * TOTAL_RECO;
            int entier = (int) theorique;

            quotas.put(theme, entier);
            restes.put(theme, theorique - entier);
            attribue += entier;

            System.out.printf(
                    " - %s : %.2f -> %d (reste %.2f)%n",
                    theme, theorique, entier, theorique - entier
            );
        }

        System.out.println("Attribués après partie entière : " + attribue);

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

            System.out.println("+1 pour " + meilleurTheme);
        }

        System.out.println("\nQuotas finaux :");
        quotas.forEach((t, q) ->
                System.out.println(" - " + t + " : " + q));

        //Récupération de tous les voyages
        List<Voyage> tousLesVoyages = voyageService.getVoyages();

        //Regroupement des voyages par thème
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

        System.out.println("\nVoyages disponibles par thème :");
        parTheme.forEach((t, l) ->
                System.out.println(" - " + t + " : " + l.size()));

        //Sélection des voyages selon les quotas
        List<Voyage> resultat = new ArrayList<>();
        int manque = 0;

        System.out.println("\nSélection finale :");
        for (String theme : quotas.keySet()) {

            List<Voyage> dispo = parTheme.get(theme);

            if (dispo == null) {
                System.out.println("Aucun voyage pour " + theme);
                manque += quotas.get(theme);
                continue;
            }

            int pris = Math.min(quotas.get(theme), dispo.size());

            for (int i = 0; i < pris; i++) {
                resultat.add(dispo.get(i));
                System.out.println(" + " + dispo.get(i).getNom());
            }

            manque += quotas.get(theme) - pris;
        }

        if (resultat.isEmpty()) {
            return Collections.emptyList();
        }

        System.out.println("\nVoyages manquants : " + manque);
        System.out.println("TOTAL RECOMMANDÉ : " + resultat.size());
        System.out.println("==============================\n");

        return resultat;
    }
}
