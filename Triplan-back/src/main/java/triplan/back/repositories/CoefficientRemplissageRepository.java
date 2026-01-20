package triplan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import triplan.back.entities.CoefficientRemplissage;
import triplan.back.entities.MoyenTransport;

import java.util.Optional;

public interface CoefficientRemplissageRepository
        extends JpaRepository<CoefficientRemplissage, Integer> {

    Optional<CoefficientRemplissage>
    findByTransportAndNiveauRemplissage(
            MoyenTransport transport,
            String niveauRemplissage
    );
}
