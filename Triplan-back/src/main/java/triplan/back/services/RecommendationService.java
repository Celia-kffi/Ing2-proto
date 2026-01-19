package triplan.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import triplan.back.entities.Voyage;

import java.util.List;

@Service
public class RecommendationService {

    private final VoyageService voyageService;

    @Autowired
    public RecommendationService(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    /**
     * Algorithme simple de recommandation :
     * - récupère tous les voyages
     * - retourne les N premiers
     */
    public List<Voyage> recommend(int nombreDeVoyages) {

        List<Voyage> voyages = voyageService.getVoyages();

        // sécurité si on demande plus que ce qu'il y a en base
        if (nombreDeVoyages > voyages.size()) {
            return voyages;
        }

        return voyages.subList(0, nombreDeVoyages);
    }
}
