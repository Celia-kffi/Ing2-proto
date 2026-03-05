package triplan.back.controllers;

import org.springframework.web.bind.annotation.*;
import triplan.back.entities.Hebergements;
import triplan.back.entities.Saison;
import triplan.back.repositories.HebergementRepository;
import triplan.back.services.EmpreinteHebergementService;

@RestController
@RequestMapping("/api/empreinte")
@CrossOrigin(origins = "http://localhost:3000")
public class EmpreinteHebergementController {

    private final EmpreinteHebergementService service;
    private final HebergementRepository hebergementRepository;

    public EmpreinteHebergementController(
            EmpreinteHebergementService service,
            HebergementRepository hebergementRepository
    ) {
        this.service = service;
        this.hebergementRepository = hebergementRepository;
    }

    @PostMapping("/calcul")
    public double calculer(
            @RequestParam Integer hebergementId,
            @RequestParam int duree,
            @RequestParam Saison saison,
            @RequestParam boolean chauffage
    ) {

        Hebergements hebergement = hebergementRepository
                .findById(hebergementId)
                .orElseThrow(() -> new RuntimeException("Hébergement introuvable"));

        return service.calculerEmpreinte(
                hebergement,
                duree,
                saison,
                chauffage
        );
    }
}
