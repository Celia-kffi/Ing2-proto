package triplan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triplan.back.entities.Profil;

@Repository //Responsable sur l'accés des données
public interface ProfilRepository extends JpaRepository<Profil, Long> {

}
