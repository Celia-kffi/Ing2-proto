package triplan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import triplan.back.entities.Voyage;

import java.util.List;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {

}
