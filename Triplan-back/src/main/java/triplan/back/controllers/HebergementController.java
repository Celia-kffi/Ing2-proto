package triplan.back.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import triplan.back.entities.Hebergements;
import triplan.back.repositories.HebergementRepository;

import java.util.List;

@RestController
@RequestMapping("/api/hebergements")
@CrossOrigin(origins = "http://localhost:3000")
public class HebergementController {

    private final HebergementRepository hebergementRepository;

    public HebergementController(HebergementRepository hebergementRepository) {
        this.hebergementRepository = hebergementRepository;
    }

    @GetMapping
    public ResponseEntity<List<Hebergements>> getHebergements(
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String destination
    ) {
        List<Hebergements> hebergements;

        if (ville != null && !ville.isBlank()) {
            hebergements = hebergementRepository
                    .findByVilleIgnoreCaseOrDestinationIgnoreCase(ville, ville);
        } else if (destination != null && !destination.isBlank()) {
            hebergements = hebergementRepository.findByDestinationIgnoreCase(destination);
        } else {
            hebergements = hebergementRepository.findAll();
        }

        return ResponseEntity.ok(hebergements);
    }
}