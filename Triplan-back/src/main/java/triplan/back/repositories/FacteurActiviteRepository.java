package triplan.back.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triplan.back.entities.FacteurActivite;
import java.util.Optional;

@Repository
public interface FacteurActiviteRepository extends JpaRepository<FacteurActivite, Long> {

    Optional<FacteurActivite> findByTypeActivite(String typeActivite);
}
