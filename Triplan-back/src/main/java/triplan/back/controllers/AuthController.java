package triplan.back.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import triplan.back.entities.Profil;
import triplan.back.repositories.ProfilRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthController {

    private final ProfilRepository profilRepository;

    public AuthController(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Profil loginData) {

        Profil profil = profilRepository.findByAdresseMail(loginData.getAdresseMail());

        if (profil == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email incorrect");
        }

        if (!profil.getMotDePasse().equals(loginData.getMotDePasse())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Mot de passe incorrect");
        }

        return ResponseEntity.ok(profil);
    }
}