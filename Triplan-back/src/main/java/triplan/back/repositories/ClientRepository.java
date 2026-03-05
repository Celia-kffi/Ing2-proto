package triplan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import triplan.back.entities.ClientEntities;

public interface ClientRepository extends JpaRepository<ClientEntities, Long> {
}