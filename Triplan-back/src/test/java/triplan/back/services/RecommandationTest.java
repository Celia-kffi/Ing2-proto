package triplan.back.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import triplan.back.dto.ProfilForm;
import triplan.back.entities.Voyage;
import triplan.back.repositories.VoyageRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommandationTest {

    private VoyageRepository voyageRepository;
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        voyageRepository = mock(VoyageRepository.class);
        recommendationService = new RecommendationService(voyageRepository);
    }

    // Test 1 : vérifier qu'on retourne bien 6 recommandations max
    @Test
    void doit_retourner_max_6_voyages() {
        List<Voyage> voyages = Arrays.asList(
                creerVoyage(1L, "aventure", 1000),
                creerVoyage(2L, "luxe", 2000),
                creerVoyage(3L, "culture", 800),
                creerVoyage(4L, "detente", 900),
                creerVoyage(5L, "aventure", 1200),
                creerVoyage(6L, "luxe", 1500),
                creerVoyage(7L, "culture", 700)
        );

        when(voyageRepository.findAll()).thenReturn(voyages);

        ProfilForm p = creerProfil();

        List<Voyage> result = recommendationService.recommander(p);

        assertTrue(result.size() <= 6);
    }

    // Test 2 : vérifier que les voyages sont triés par score
    @Test
    void voyages_doivent_etre_tries_par_score() {
        Voyage v1 = creerVoyage(1L, "aventure", 1000);
        Voyage v2 = creerVoyage(2L, "luxe", 5000);

        when(voyageRepository.findAll()).thenReturn(Arrays.asList(v1, v2));

        ProfilForm p = creerProfil();
        p.setTypeExperience("aventure");

        List<Voyage> result = recommendationService.recommander(p);

        assertEquals("aventure", result.get(0).getTheme());
    }

    // Test 3 : vérifier qu'un voyage proche du budget est favorisé
    @Test
    void voyage_budget_proche_doit_etre_mieux_note() {
        Voyage v1 = creerVoyage(1L, "aventure", 1000);
        Voyage v2 = creerVoyage(2L, "aventure", 3000);

        when(voyageRepository.findAll()).thenReturn(Arrays.asList(v1, v2));

        ProfilForm p = creerProfil();
        p.setBudgetProfil(1000);

        List<Voyage> result = recommendationService.recommander(p);

        assertEquals(1000, result.get(0).getPrix());
    }

    // Test 4 : vérifier qu'on ne plante pas si valeurs null
    @Test
    void ne_doit_pas_planter_si_donnees_nulles() {
        Voyage v1 = new Voyage();
        Voyage v2 = new Voyage();

        when(voyageRepository.findAll()).thenReturn(Arrays.asList(v1, v2));

        ProfilForm p = new ProfilForm();

        List<Voyage> result = recommendationService.recommander(p);

        assertNotNull(result);
    }


    private Voyage creerVoyage(Long id, String theme, Integer prix) {
        Voyage v = new Voyage();
        v.setId(id);
        v.setTheme(theme);
        v.setPrix(prix);
        v.setCategorie("ski");
        v.setThemePrincipal("montagne");
        return v;
    }

    private ProfilForm creerProfil() {
        ProfilForm p = new ProfilForm();
        p.setTypeExperience("aventure");
        p.setBudgetProfil(1000);
        p.setDureeMoyenne(5);
        p.setDuree("Semaine");
        p.setExperience("Frisson");
        p.setEnvironnement("montagne");
        p.setActivite("Sport");
        p.setBudget("Moyen");
        p.setConfort("Classique");
        p.setCompagnie("Amis");
        p.setRythme("Intense");
        return p;
    }
}
