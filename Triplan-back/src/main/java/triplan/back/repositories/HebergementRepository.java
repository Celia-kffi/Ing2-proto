package triplan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triplan.back.entities.Hebergements;

import java.util.List;

@Repository
public interface HebergementRepository extends JpaRepository<Hebergements, Integer> {
    List<Hebergements> findByDestinationIgnoreCase(String destination);
    List<Hebergements> findByVilleIgnoreCaseOrDestinationIgnoreCase(String ville, String destination);
}