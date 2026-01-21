package triplan.back.services;

import org.springframework.stereotype.Service;
import triplan.back.entities.Profil;
import triplan.back.repositories.ProfilRepository;

import java.util.List;

@Service
public class ProfilService {

    private final ProfilRepository profilRepository;

    public ProfilService(ProfilRepository profilRepository) {
        this.profilRepository = profilRepository;
    }


    public Profil getProfilById(Long id) {
        return profilRepository.findById(id).orElse(null);
    }


    public List<Profil> getAll() {
        return profilRepository.findAll();
    }
}
