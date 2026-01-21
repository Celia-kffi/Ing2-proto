package triplan.back.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import triplan.back.entities.Profil;
import triplan.back.services.ProfilService;

import java.util.List;

@RestController
@RequestMapping("/api/profils")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfilController {

    private final ProfilService profilService;

    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

    @GetMapping
    public List<Profil> getAll() {
        return profilService.getAll();
    }
}





