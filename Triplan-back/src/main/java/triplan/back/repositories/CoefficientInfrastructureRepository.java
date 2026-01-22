package triplan.back.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triplan.back.entities.CoefficientInfrastructure;
import triplan.back.entities.MoyenTransport;
import java.util.Optional;

@Repository
public interface CoefficientInfrastructureRepository
        extends JpaRepository<CoefficientInfrastructure, Long> {

    Optional<CoefficientInfrastructure>
    findByTransportAndTypeInfrastructure(
            MoyenTransport transport,
            String typeInfrastructure
    );
}


