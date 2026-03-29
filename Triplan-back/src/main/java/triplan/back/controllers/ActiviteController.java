package triplan.back.controllers;

import org.springframework.web.bind.annotation.*;
import triplan.back.entities.Activite;
import triplan.back.repositories.ActiviteRepository;
import triplan.back.services.ActiviteService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/activites")
@CrossOrigin(origins = "http://localhost:3000")
public class ActiviteController {

    private final ActiviteService service;
    private final ActiviteRepository repository;

    public ActiviteController(ActiviteService service, ActiviteRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/{id}/empreinte")
    public Map<String, Object> calculEmpreinte(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int nbPersonnes
    ) {
        Activite activite = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));

        return service.calculEmpreinteAvecDetails(activite, nbPersonnes);
    }
}