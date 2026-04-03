package triplan.back.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import triplan.back.entities.CoefficientInfrastructure;
import triplan.back.entities.CoefficientRemplissage;
import triplan.back.entities.EmpreinteCarbone;
import triplan.back.entities.MoyenTransport;
import triplan.back.repositories.CoefficientInfrastructureRepository;
import triplan.back.repositories.CoefficientRemplissageRepository;
import triplan.back.repositories.EmpreinteCarboneRepository;
import triplan.back.repositories.MoyenTransportRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpreinteServiceTest {

    private MoyenTransportRepository transportRepo;
    private CoefficientInfrastructureRepository infraRepo;
    private CoefficientRemplissageRepository remplissageRepo;
    private EmpreinteCarboneRepository empreinteRepo;
    private EmpreinteCarboneService service;

    @BeforeEach
    void setUp() {
        transportRepo = mock(MoyenTransportRepository.class);
        infraRepo = mock(CoefficientInfrastructureRepository.class);
        remplissageRepo = mock(CoefficientRemplissageRepository.class);
        empreinteRepo = mock(EmpreinteCarboneRepository.class);

        service = new EmpreinteCarboneService(
                transportRepo, infraRepo, remplissageRepo, empreinteRepo
        );
    }

    // Test 1 : vérifier le choix automatique du transport selon la distance
    @Test
    void choisirTransport_selon_distance() {
        MoyenTransport marche = new MoyenTransport();
        marche.setNom("Marche");

        MoyenTransport velo = new MoyenTransport();
        velo.setNom("Vélo");

        when(transportRepo.findByNom("Marche")).thenReturn(Optional.of(marche));
        when(transportRepo.findByNom("Vélo")).thenReturn(Optional.of(velo));

        MoyenTransport t1 = service.choisirTransport(1.0);
        MoyenTransport t2 = service.choisirTransport(3.0);

        assertEquals("Marche", t1.getNom());
        assertEquals("Vélo", t2.getNom());
    }

    // Test 2 : calcul de l'empreinte avec transport donné
    @Test
    void calculEmpreinte_doite_calculer_correctement() {
        MoyenTransport voiture = new MoyenTransport();
        voiture.setNom("Voiture essence");
        voiture.setFacteurEmission(0.2);

        MoyenTransport avion = new MoyenTransport();
        avion.setNom("Avion");
        avion.setFacteurEmission(0.3);

        when(transportRepo.findByNom("Voiture essence")).thenReturn(Optional.of(voiture));
        when(transportRepo.findAll()).thenReturn(List.of(voiture, avion));

        CoefficientInfrastructure ci = new CoefficientInfrastructure();
        ci.setCoefficient(1.5);
        when(infraRepo.findByTransportAndTypeInfrastructure(voiture, "Route"))
                .thenReturn(Optional.of(ci));

        CoefficientRemplissage cr = new CoefficientRemplissage();
        cr.setCoefficient(0.8);
        when(remplissageRepo.findByTransportAndNiveauRemplissage(voiture, "Moyen"))
                .thenReturn(Optional.of(cr));

        Map<String, Object> data = Map.of(
                "distance", 100,
                "infrastructure", "Route",
                "remplissage", "Moyen",
                "transport", "Voiture essence"
        );

        Map<String, Object> result = service.calculEmpreinte(data);

        assertEquals("Voiture essence", result.get("transport"));
        assertEquals(100.0, result.get("distance"));
        assertEquals(0.2, result.get("facteur"));
        assertEquals(1.5, result.get("coeffInfrastructure"));
        assertEquals(0.8, result.get("coeffRemplissage"));

        double expected = 100 * 0.2 * 1.5 * 0.8;
        assertEquals(expected, (double) result.get("empreinte"));

        // Vérifier que l'empreinte a été sauvegardée
        verify(empreinteRepo, times(1)).save(any(EmpreinteCarbone.class));
    }

    // Test 3 : vérifie que le transport auto choisi si non fourni
    @Test
    void calculEmpreinte_choisit_transport_auto_si_non_precise() {
        MoyenTransport marche = new MoyenTransport();
        marche.setNom("Marche");
        marche.setFacteurEmission(0.0);

        MoyenTransport autre = new MoyenTransport();
        autre.setNom("Avion");
        autre.setFacteurEmission(0.3);

        when(transportRepo.findByNom("Marche")).thenReturn(Optional.of(marche));
        when(transportRepo.findAll()).thenReturn(List.of(marche, autre));

        Map<String, Object> data = Map.of(
                "distance", 1.0,
                "infrastructure", "Chemin",
                "remplissage", "Bas",
                "transport", ""
        );

        Map<String, Object> result = service.calculEmpreinte(data);

        assertEquals("Marche", result.get("transport"));
    }
}