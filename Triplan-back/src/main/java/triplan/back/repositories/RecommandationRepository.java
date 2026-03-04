package triplan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import triplan.back.entities.Recommandation;
import java.util.Optional;

public interface RecommandationRepository extends JpaRepository<Recommandation, Long> {
    Optional<Recommandation> findTopByClientIdOrderByDateGenerationDesc(Long clientId);
}


