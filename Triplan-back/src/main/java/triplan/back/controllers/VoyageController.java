package triplan.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import triplan.back.entities.Voyage;
import triplan.back.services.VoyageService;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/voyage")
public class VoyageController {

    private final VoyageService voyageService;

    @Autowired
    public VoyageController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @GetMapping
    public List<Voyage> getAllVoyages() {
        return voyageService.getVoyages();
    }
}
