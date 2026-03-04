package triplan.back.services;

import org.springframework.stereotype.Service;
import triplan.back.dto.*;
import triplan.back.entities.Activite;
import triplan.back.repositories.ActiviteRepository;
import triplan.back.dto.MultiDayItineraryResponse;


import java.util.*;

@Service
public class ItineraryService {

    private ActiviteRepository activiteRepository;

    public ItineraryService(ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
    }


    public ItineraryResponse calculerItineraireOptimal(ItineraryRequest request) {

        // On récupère les activités sélectionnées
        List<Long> activiteIds = request.getActiviteIds();
        List<Activite> activitesBDD =
                activiteRepository.findAllById(activiteIds);

        if (activitesBDD.isEmpty()) {
            throw new IllegalArgumentException("Aucune activité trouvée");
        }

        Map<Long, Activite> mapActivites = new HashMap<>();
        for (Activite a : activitesBDD) {
            mapActivites.put(a.getId(), a);
        }

        List<Activite> activites = new ArrayList<>();
        for (Long id : activiteIds) {
            Activite a = mapActivites.get(id);
            if (a != null) {
                activites.add(a);
            }
        }

        Activite depart = determinerPointDepart(activites, request.getPointDepartId());

        // Calcul de l’itinéraire avec l’algorithme du plus proche voisin
        List<Activite> itineraire = calculerItinerairePlusProche(activites, depart);

        return construireReponse(itineraire);
    }

    //On cherche l'activité la plus proche
    private List<Activite> calculerItinerairePlusProche(
            List<Activite> activites, Activite depart) {

        List<Activite> itineraire = new ArrayList<>();
        Set<Long> activitesVisitees = new HashSet<>();

        Activite actuelle = depart;
        itineraire.add(actuelle);
        activitesVisitees.add(actuelle.getId());


        while (activitesVisitees.size() < activites.size()) {

            Activite plusProche = null;
            double distanceMin = Double.MAX_VALUE;

            for (Activite a : activites) {
                if (!activitesVisitees.contains(a.getId())) {
                    double distance = calculerDistance(actuelle, a);
                    if (distance < distanceMin) {
                        distanceMin = distance;
                        plusProche = a;
                    }
                }
            }

            if (plusProche != null) {
                itineraire.add(plusProche);
                activitesVisitees.add(plusProche.getId());
                actuelle = plusProche;
            }
        }

        return itineraire;
    }

    //Formule de Haversine pour calculer la distance entre deux points sur la Terre
    private double calculerDistance(Activite a1, Activite a2) {

        double lat1 = a1.getLatitude().doubleValue();
        double lon1 = a1.getLongitude().doubleValue();
        double lat2 = a2.getLatitude().doubleValue();
        double lon2 = a2.getLongitude().doubleValue();

        final int rayon_Terre_km = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return rayon_Terre_km * c;
    }

    //si un point de depart est fourni on le prend sinon on commence par la premiere activité
    private Activite determinerPointDepart(List<Activite> activites, Long pointDepartId) {

        if (pointDepartId != null) {
            for (Activite a : activites) {
                if (a.getId().equals(pointDepartId)) {
                    return a;
                }
            }
        }

        return activites.get(0);
    }


    private ItineraryResponse construireReponse(List<Activite> itineraire) {

        List<ActiviteEtape> etapes = new ArrayList<>();
        double distanceTotale = 0;
        int dureeTotale = 0;

        for (int i = 0; i < itineraire.size(); i++) {

            Activite activite = itineraire.get(i);
            Double distance = null;
            Integer tempsTrajet = null;

            if (i > 0) {
                distance = calculerDistance(itineraire.get(i - 1), activite);
                distanceTotale += distance;

                tempsTrajet = (int) Math.ceil(distance * 12);
                dureeTotale += tempsTrajet;
            }

            dureeTotale += activite.getDureeVisiteMinutes();

            ActiviteEtape etape = new ActiviteEtape(
                    i + 1,
                    activite.getId(),
                    activite.getNom(),
                    activite.getLatitude(),
                    activite.getLongitude(),
                    activite.getDureeVisiteMinutes(),
                    distance,
                    tempsTrajet,
                    activite.getTypeActivite()
            );

            etapes.add(etape);
        }

        return new ItineraryResponse(
                etapes,
                Math.round(distanceTotale * 100.0) / 100.0,
                dureeTotale
        );
    }



    public MultiDayItineraryResponse calculerItineraireMultiJours(MultiDayItineraryRequest request) {
        List<Long> activiteIds = request.getActiviteIds();
        List<Activite> activitesBDD = activiteRepository.findAllById(activiteIds);

        if (activitesBDD.isEmpty()) {
            throw new IllegalArgumentException("Aucune activité trouvée pour les IDs fournis");
        }

        Map<Long, Activite> mapActivites = new HashMap<>();
        for (Activite a : activitesBDD) {
            mapActivites.put(a.getId(), a);
        }

        List<Activite> activites = new ArrayList<>();
        for (Long id : activiteIds) {
            Activite a = mapActivites.get(id);
            if (a != null) {
                activites.add(a);
            }
        }

        Activite depart = determinerPointDepart(activites, request.getPointDepartId());

        List<Activite> itineraireOptimal = calculerItinerairePlusProche(activites, depart);

        // On répartit les activités sur plusieurs jours
        List<JourItineraire> jours = repartirParJours(
                itineraireOptimal,
                request.getNbJours()
        );

        // On calcul les totaux distance et durée pour tout le séjour
        double distanceTotale = jours.stream()
                .mapToDouble(JourItineraire::getDistanceTotaleKm)
                .sum();

        int dureeTotale = jours.stream()
                .mapToInt(JourItineraire::getDureeTotaleMinutes)
                .sum();

        return new MultiDayItineraryResponse(
                request.getNbJours(),
                jours,
                Math.round(distanceTotale * 100.0) / 100.0,
                dureeTotale
        );
    }

    private List<JourItineraire> repartirParJours(
            List<Activite> itineraireOptimal,
            int nbJours
    ) {
        // Calcul de la durée totale de toutes les activités
        int dureeTotale = 0;
        for (Activite a : itineraireOptimal) {
            dureeTotale += a.getDureeVisiteMinutes();
        }

        // Durée qu'on vise par jour et minimum d'activités par jour
        int dureeCibleParJour = dureeTotale / nbJours;
        int activitesMinParJour = itineraireOptimal.size() / nbJours;

        List<JourItineraire> jours = new ArrayList<>();
        List<ActiviteEtape> activitesJourActuel = new ArrayList<>();

        int numeroJour = 1;
        int dureeCumuleeJour = 0;
        double distanceCumuleeJour = 0.0;
        int ordreGlobal = 1;

        for (int i = 0; i < itineraireOptimal.size(); i++) {
            Activite activite = itineraireOptimal.get(i);

            // Calcul du trajet depuis l'activité précédente
            int tempsTrajet = 0;
            double distanceTrajet = 0.0;

            if (!activitesJourActuel.isEmpty()) {
                Activite precedente = itineraireOptimal.get(i - 1);
                distanceTrajet = calculerDistance(precedente, activite);
                tempsTrajet = (int) Math.ceil(distanceTrajet * 12);
            }

            // On ajoute l'activité au jour courant
            ActiviteEtape etape = new ActiviteEtape(
                    ordreGlobal++,
                    activite.getId(),
                    activite.getNom(),
                    activite.getLatitude(),
                    activite.getLongitude(),
                    activite.getDureeVisiteMinutes(),
                    distanceTrajet > 0 ? distanceTrajet : null,
                    tempsTrajet > 0 ? tempsTrajet : null,
                    activite.getTypeActivite()
            );

            activitesJourActuel.add(etape);
            dureeCumuleeJour += activite.getDureeVisiteMinutes() + tempsTrajet;
            distanceCumuleeJour += distanceTrajet;

            // On vérifie si on peut passer au jour suivant
            boolean cibleDureeAtteinte = dureeCumuleeJour >= dureeCibleParJour;
            boolean minimumActivitesAtteint = activitesJourActuel.size() >= activitesMinParJour;
            boolean resteDesJours = numeroJour < nbJours;

            if (cibleDureeAtteinte && minimumActivitesAtteint && resteDesJours) {
                // On sauvegarde le jour et on repart à zéro
                jours.add(new JourItineraire(
                        numeroJour,
                        new ArrayList<>(activitesJourActuel),
                        dureeCumuleeJour,
                        Math.round(distanceCumuleeJour * 100.0) / 100.0
                ));
                numeroJour++;
                activitesJourActuel.clear();
                dureeCumuleeJour = 0;
                distanceCumuleeJour = 0.0;
            }
        }

        // On ajoute le dernier jour avec les activités restantes
        if (!activitesJourActuel.isEmpty()) {
            jours.add(new JourItineraire(
                    numeroJour,
                    activitesJourActuel,
                    dureeCumuleeJour,
                    Math.round(distanceCumuleeJour * 100.0) / 100.0
            ));
        }

        return jours;
    }
}
