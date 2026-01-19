package triplan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import triplan.back.entities.ProfilThemeScore;

import java.util.List;

public interface ProfilThemeScoreRepository
        extends JpaRepository<ProfilThemeScore, Long> {

    // Récupérer tous les scores d’un profil
    List<ProfilThemeScore> findByProfilId(Long profilId);
}
