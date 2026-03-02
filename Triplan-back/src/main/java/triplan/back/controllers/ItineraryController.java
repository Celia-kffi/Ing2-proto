package triplan.back.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import triplan.back.dto.ItineraryRequest;
import triplan.back.dto.ItineraryResponse;
import triplan.back.dto.MultiDayItineraryRequest;
import triplan.back.dto.MultiDayItineraryResponse;
import triplan.back.entities.Activite;
import triplan.back.repositories.ActiviteRepository;
import triplan.back.services.ItineraryService;

import java.util.List;

@RestController
@RequestMapping("/api/itinerary")
@CrossOrigin(origins = "*")
public class ItineraryController {

    private ItineraryService itineraryService;
    private ActiviteRepository activiteRepository;

    public ItineraryController(ItineraryService itineraryService,
                               ActiviteRepository activiteRepository) {
        this.itineraryService = itineraryService;
        this.activiteRepository = activiteRepository;
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

}
