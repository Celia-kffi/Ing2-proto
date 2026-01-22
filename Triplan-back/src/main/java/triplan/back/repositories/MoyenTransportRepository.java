package triplan.back.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triplan.back.entities.MoyenTransport;

import java.util.Optional;

@Repository
public interface MoyenTransportRepository extends JpaRepository<MoyenTransport, Long> {

    Optional<MoyenTransport> findByNom(String nom);
}

