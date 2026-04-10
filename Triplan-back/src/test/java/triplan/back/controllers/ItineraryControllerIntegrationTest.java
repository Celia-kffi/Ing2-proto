package triplan.back.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import triplan.back.dto.ItineraryRequest;
import triplan.back.dto.MultiDayItineraryRequest;
import triplan.back.entities.Activite;
import triplan.back.repositories.ActiviteRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItineraryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        activiteRepository.deleteAll();

        Activite a1 = new Activite();
        a1.setNom("Tour Eiffel");
        a1.setLatitude(BigDecimal.valueOf(48.8584));
        a1.setLongitude(BigDecimal.valueOf(2.2945));
        a1.setDureeVisiteMinutes(120);
        a1.setTypeActivite("MONUMENT");
        a1.setVille("Paris");
        a1.setPrix(26.0);

        Activite a2 = new Activite();
        a2.setNom("Musée du Louvre");
        a2.setLatitude(BigDecimal.valueOf(48.8606));
        a2.setLongitude(BigDecimal.valueOf(2.3376));
        a2.setDureeVisiteMinutes(180);
        a2.setTypeActivite("MUSEE");
        a2.setVille("Paris");
        a2.setPrix(17.0);

        Activite a3 = new Activite();
        a3.setNom("Notre-Dame");
        a3.setLatitude(BigDecimal.valueOf(48.8530));
        a3.setLongitude(BigDecimal.valueOf(2.3499));
        a3.setDureeVisiteMinutes(75);
        a3.setTypeActivite("MONUMENT");
        a3.setVille("Paris");
        a3.setPrix(0.0);

        Activite a4 = new Activite();
        a4.setNom("Jardin du Luxembourg");
        a4.setLatitude(BigDecimal.valueOf(48.8466));
        a4.setLongitude(BigDecimal.valueOf(2.3372));
        a4.setDureeVisiteMinutes(60);
        a4.setTypeActivite("PARC");
        a4.setVille("Lyon");
        a4.setPrix(0.0);

        activiteRepository.saveAll(Arrays.asList(a1, a2, a3, a4));
    }

    //Test 1: GET /activites retourne toutes les activités
    @Test
    void get_toutes_activites() throws Exception {
        mockMvc.perform(get("/api/itinerary/activites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    //Test 2: GET /activites/ville/Paris retourne uniquement les activités de Paris
    @Test
    void get_activites_par_ville_retourne_uniquement_ville_demandee() throws Exception {
        mockMvc.perform(get("/api/itinerary/activites/ville/Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].ville").value("Paris"));
    }


    //Test 3: POST /calculate retourne un itinéraire valide
    @Test
    void post_calculate_retourne_itineraire_valide() throws Exception {
        List<Activite> activites = activiteRepository.findAll();
        List<Long> ids = activites.stream()
                .filter(a -> a.getVille().equals("Paris"))
                .map(Activite::getId)
                .toList();

        ItineraryRequest request = new ItineraryRequest();
        request.setActiviteIds(ids);
        request.setPointDepartId(null);

        mockMvc.perform(post("/api/itinerary/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itineraire").isArray())
                .andExpect(jsonPath("$.itineraire.length()").value(3))
                .andExpect(jsonPath("$.distanceTotaleKm").isNumber())
                .andExpect(jsonPath("$.dureeTotaleMinutes").isNumber());
    }

    //Test 4: POST /calculate-multi-days retourne un planning multi-jours valide
    @Test
    void post_calculate_multi_days_retourne_planning_valide() throws Exception {
        List<Activite> activites = activiteRepository.findAll();
        List<Long> ids = activites.stream()
                .filter(a -> a.getVille().equals("Paris"))
                .map(Activite::getId)
                .toList();

        MultiDayItineraryRequest request = new MultiDayItineraryRequest();
        request.setActiviteIds(ids);
        request.setPointDepartId(null);
        request.setNbJours(2);

        mockMvc.perform(post("/api/itinerary/calculate-multi-days")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jours").isArray())
                .andExpect(jsonPath("$.jours.length()").value(2))
                .andExpect(jsonPath("$.distanceTotaleKm").isNumber());
    }
}