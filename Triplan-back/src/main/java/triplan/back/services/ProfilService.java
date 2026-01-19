package triplan.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import triplan.back.entities.Profil;
import triplan.back.repositories.ProfilRepository;

import java.util.List;

@Service //cette classe va etre une service classe
public class ProfilService {

    private final ProfilRepository profilRepository;

    //Appelle le repository, récupère les données dans la base, retourne la liste au controller
    @Autowired
    public ProfilService(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }

    public List<Profil> getProfils() {
        return profilRepository.findAll();
    }
}
