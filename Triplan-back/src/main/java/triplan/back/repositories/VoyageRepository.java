package triplan.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import triplan.back.entities.Voyage;

import java.util.List;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {

    // Récupérer tous les voyages d’un thème donné
    List<Voyage> findByThemePrincipal(String themePrincipal);

    // (optionnel) si plus tard tu veux filtrer par budget
    List<Voyage> findByThemePrincipalAndBudgetMoyenLessThanEqual(
            String themePrincipal,
            Integer budgetMoyen
    );
}
