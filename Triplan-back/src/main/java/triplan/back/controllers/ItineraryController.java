package triplan.back.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import triplan.back.dto.ItineraryRequest;
import triplan.back.dto.ItineraryResponse;
import triplan.back.dto.MultiDayItineraryRequest;
import triplan.back.dto.MultiDayItineraryResponse;
import triplan.back.entities.Activite;
import triplan.back.entities.Recommandation;
import triplan.back.repositories.ActiviteRepository;
import triplan.back.repositories.HebergementRepository;
import triplan.back.services.ItineraryService;
import triplan.back.repositories.RecommandationRepository;
import triplan.back.entities.Hebergements;

import java.util.List;

@RestController
@RequestMapping("/api/itinerary")
@CrossOrigin(origins = "*")
public class ItineraryController {

    private ItineraryService itineraryService;
    private ActiviteRepository activiteRepository;
    private RecommandationRepository recommandationRepository;
    private HebergementRepository hebergementRepository;

    public ItineraryController(ItineraryService itineraryService,
                               ActiviteRepository activiteRepository,
                               RecommandationRepository recommandationRepository,
                               HebergementRepository hebergementRepository
                               ) {
        this.itineraryService = itineraryService;
        this.activiteRepository = activiteRepository;
        this.recommandationRepository = recommandationRepository;
        this.hebergementRepository = hebergementRepository;
    }

    @GetMapping("/activites")
    public List<Activite> getAllActivites() {
        return activiteRepository.findAll();
    }

    @GetMapping("/activites/{id}")
    public Activite getActiviteById(@PathVariable Long id) {
        return activiteRepository.findById(id).orElse(null);
    }

    @GetMapping("/activites/type/{type}")
    public List<Activite> getActivitesByType(@PathVariable String type) {
        return activiteRepository.findByTypeActivite(type);
    }

    @PostMapping("/calculate")
    public ItineraryResponse calculateOptimalItinerary(
            @RequestBody ItineraryRequest request) {
        return itineraryService.calculerItineraireOptimal(request);
    }

    @PostMapping("/calculate-multi-days")
    public ResponseEntity<MultiDayItineraryResponse> calculateMultiDayItinerary(
            @RequestBody MultiDayItineraryRequest request) {
        try {
            MultiDayItineraryResponse response = itineraryService.calculerItineraireMultiJours(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/recommandations/derniere-ville/{clientId}")
    public ResponseEntity<String> getDerniereVilleRecommandee(@PathVariable Long clientId) {
        Recommandation reco = recommandationRepository
                .findTopByClientIdOrderByDateGenerationDesc(clientId)
                .orElseThrow(() -> new RuntimeException("Aucune recommandation trouvée"));

        String ville = reco.getVoyage().getDestination();
        return ResponseEntity.ok(ville);
    }

    @GetMapping("/activites/ville/{ville}")
    public ResponseEntity<List<Activite>> getActivitesByVille(@PathVariable String ville) {
        return ResponseEntity.ok(activiteRepository.findByVille(ville));
    }
    @GetMapping("/hebergements")
    public ResponseEntity<List<Hebergements>> getHebergementsByVille(@RequestParam String ville) {
        List<Hebergements> hebergements = hebergementRepository.findByVilleIgnoreCaseOrDestinationIgnoreCase(ville, ville);
        return ResponseEntity.ok(hebergements);
    }

}
