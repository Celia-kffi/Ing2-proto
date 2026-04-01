package triplan.back.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import triplan.back.dto.ItineraryRequest;
import triplan.back.dto.ItineraryResponse;
import triplan.back.entities.Activite;
import triplan.back.repositories.ActiviteRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItineraryServiceTest {

    private ActiviteRepository activiteRepository;
    private ItineraryService itineraryService;

    @BeforeEach
    void setUp() {
        activiteRepository = mock(ActiviteRepository.class);
        itineraryService = new ItineraryService(activiteRepository);
    }

    //Test 1: on vérifie que l'itinéraire commence bien par le point de départ choisi
    @Test
    void itineraire_doit_commencer_par_le_point_de_depart() {
        Activite a1 = creerActivite(1L, "Tour Eiffel", 48.8584, 2.2945);
        Activite a2 = creerActivite(2L, "Louvre", 48.8606, 2.3376);
        Activite a3 = creerActivite(3L, "Notre-Dame", 48.8530, 2.3499);

        when(activiteRepository.findAllById(anyList()))
                .thenReturn(Arrays.asList(a1, a2, a3));

        ItineraryRequest request = new ItineraryRequest();
        request.setActiviteIds(Arrays.asList(1L, 2L, 3L));
        request.setPointDepartId(2L);

        ItineraryResponse response = itineraryService.calculerItineraireOptimal(request);

        assertEquals("Louvre", response.getItineraire().get(0).getNom());
    }

    //Test 2: on vérifie bien que la distance totale est positive et cohérente
    @Test
    void distance_totale_doit_etre_positive() {
        Activite a1 = creerActivite(1L, "Tour Eiffel", 48.8584, 2.2945);
        Activite a2 = creerActivite(2L, "Louvre", 48.8606, 2.3376);

        when(activiteRepository.findAllById(anyList()))
                .thenReturn(Arrays.asList(a1, a2));

        ItineraryRequest request = new ItineraryRequest();
        request.setActiviteIds(Arrays.asList(1L, 2L));
        request.setPointDepartId(null);

        ItineraryResponse response = itineraryService.calculerItineraireOptimal(request);

        assertTrue(response.getDistanceTotaleKm() > 0);
    }

    //Test 3: on vérifie que le nombre d'étapes correspond bien au nombre d'activités sélectionnées
    @Test
    void nombre_etapes_doit_correspondre_aux_activites_selectionnees() {
        Activite a1 = creerActivite(1L, "Tour Eiffel", 48.8584, 2.2945);
        Activite a2 = creerActivite(2L, "Louvre", 48.8606, 2.3376);
        Activite a3 = creerActivite(3L, "Notre-Dame", 48.8530, 2.3499);

        when(activiteRepository.findAllById(anyList()))
                .thenReturn(Arrays.asList(a1, a2, a3));

        ItineraryRequest request = new ItineraryRequest();
        request.setActiviteIds(Arrays.asList(1L, 2L, 3L));
        request.setPointDepartId(null);

        ItineraryResponse response = itineraryService.calculerItineraireOptimal(request);

        assertEquals(3, response.getItineraire().size());
    }

    //Test 4: on vérifie que la répartition multi-jours ne génère pas de jour vide
    @Test
    void repartition_multi_jours_ne_doit_pas_avoir_de_jour_vide() {
        Activite a1 = creerActivite(1L, "Tour Eiffel", 48.8584, 2.2945);
        Activite a2 = creerActivite(2L, "Louvre", 48.8606, 2.3376);
        Activite a3 = creerActivite(3L, "Notre-Dame", 48.8530, 2.3499);
        Activite a4 = creerActivite(4L, "Sacré-Coeur", 48.8867, 2.3431);

        when(activiteRepository.findAllById(anyList()))
                .thenReturn(Arrays.asList(a1, a2, a3, a4));

        triplan.back.dto.MultiDayItineraryRequest request =
                new triplan.back.dto.MultiDayItineraryRequest();
        request.setActiviteIds(Arrays.asList(1L, 2L, 3L, 4L));
        request.setPointDepartId(null);
        request.setNbJours(4);

        triplan.back.dto.MultiDayItineraryResponse response =
                itineraryService.calculerItineraireMultiJours(request);

        // Aucun jour ne doit être vide
        response.getJours().forEach(jour ->
                assertFalse(jour.getActivites().isEmpty(),
                        "Le jour " + jour.getNumeroJour() + " ne doit pas être vide")
        );
    }

    private Activite creerActivite(Long id, String nom, double lat, double lng) {
        Activite a = new Activite();
        a.setId(id);
        a.setNom(nom);
        a.setLatitude(BigDecimal.valueOf(lat));
        a.setLongitude(BigDecimal.valueOf(lng));
        a.setDureeVisiteMinutes(60);
        a.setTypeActivite("MONUMENT");
        a.setVille("Paris");
        a.setPrix(10.0);
        return a;
    }
}