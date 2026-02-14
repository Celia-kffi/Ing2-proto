package triplan.back.controllers;

import org.springframework.web.bind.annotation.*;
import triplan.back.entities.CoefficientInfrastructure;
import triplan.back.entities.CoefficientRemplissage;
import triplan.back.entities.MoyenTransport;
import triplan.back.repositories.CoefficientInfrastructureRepository;
import triplan.back.repositories.CoefficientRemplissageRepository;
import triplan.back.repositories.MoyenTransportRepository;
import triplan.back.entities.EmpreinteCarbone;
import triplan.back.repositories.EmpreinteCarboneRepository;
import triplan.back.services.EmpreinteCarboneService;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/empreinte")
public class empreinteControllers {

    private final EmpreinteCarboneService empreinteService;

    public empreinteControllers(EmpreinteCarboneService empreinteService) {
        this.empreinteService = empreinteService;
    }

    @PostMapping("/calcul")
    public Map<String, Object> calcul(@RequestBody Map<String, Object> data) {
        return empreinteService.calculEmpreinte(data);
    }
}

