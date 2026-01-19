package triplan.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import triplan.back.entities.Profil;
import triplan.back.services.ProfilService;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/profil")
public class ProfilController {

    private final ProfilService profilService;

    @Autowired //injecter la variable précédente dans les variables de la fonction qui suit (le service dan le controller)
    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }
    //elle écoute une requête http GET
    //elle appelle le service et retourne l'objet
    @GetMapping
    public List<Profil> getAllProfils(){
        return profilService.getProfils();
    }

}
