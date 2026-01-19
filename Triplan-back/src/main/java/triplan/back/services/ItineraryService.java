package triplan.back.services;

import org.springframework.stereotype.Service;
import triplan.back.dto.ActiviteEtape;
import triplan.back.dto.ItineraryRequest;
import triplan.back.dto.ItineraryResponse;
import triplan.back.entities.Activite;
import triplan.back.repositories.ActiviteRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItineraryService {

    private ActiviteRepository activiteRepository;

    public ItineraryService(ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
    }


    public ItineraryResponse calculerItineraireOptimal(ItineraryRequest request) {

        // On récupère les activités sélectionnées
        List<Activite> activites =
                activiteRepository.findAllById(request.getActiviteIds());

        if (activites.isEmpty()) {
            throw new IllegalArgumentException("Aucune activité trouvée");
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
                dureeTotale,
                "Nearest Neighbor"
        );
    }
}
