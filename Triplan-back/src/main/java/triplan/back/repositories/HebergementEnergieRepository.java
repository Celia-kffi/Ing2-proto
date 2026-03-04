package triplan.back.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import triplan.back.entities.HebergementEnergie;
import triplan.back.entities.Hebergements;

import java.util.Optional;

public interface HebergementEnergieRepository extends JpaRepository<HebergementEnergie, Long> {

    Optional<HebergementEnergie> findByHebergement(Hebergements hebergement);

}
