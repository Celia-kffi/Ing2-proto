package triplan.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import triplan.back.entities.ProfilThemeScore;
import triplan.back.repositories.ProfilThemeScoreRepository;

import java.util.List;

@Service
public class ProfilThemeScoreService {

    private final ProfilThemeScoreRepository profilThemeScoreRepository;

    @Autowired
    public ProfilThemeScoreService(ProfilThemeScoreRepository profilThemeScoreRepository) {
        this.profilThemeScoreRepository = profilThemeScoreRepository;
    }

    public List<ProfilThemeScore> getProfilThemeScores() {
        return profilThemeScoreRepository.findAll();
    }

    public List<ProfilThemeScore> getByProfilId(Long profilId) {
        return profilThemeScoreRepository.findByProfilId(profilId);
    }
}
