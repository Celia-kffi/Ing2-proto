package triplan.back.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import triplan.back.entities.CoefficientInfrastructure;
import triplan.back.entities.CoefficientRemplissage;
import triplan.back.entities.EmpreinteCarbone;
import triplan.back.entities.MoyenTransport;
import triplan.back.repositories.CoefficientInfrastructureRepository;
import triplan.back.repositories.CoefficientRemplissageRepository;
import triplan.back.repositories.EmpreinteCarboneRepository;
import triplan.back.repositories.MoyenTransportRepository;

import java.util.Map;
@Service
public class EmpreinteCarboneService {
    private final MoyenTransportRepository transportRepo;
    private final CoefficientInfrastructureRepository infraRepo;
    private final CoefficientRemplissageRepository remplissageRepo;
    private final EmpreinteCarboneRepository empreinteRepo;

    public EmpreinteCarboneService(
            MoyenTransportRepository transportRepo,
            CoefficientInfrastructureRepository infraRepo,
            CoefficientRemplissageRepository remplissageRepo,
            EmpreinteCarboneRepository empreinteRepo
    ) {
        this.transportRepo = transportRepo;
        this.infraRepo = infraRepo;
        this.remplissageRepo = remplissageRepo;
        this.empreinteRepo = empreinteRepo;
    }


    public Map<String, Object> calculEmpreinte(@RequestBody Map<String, Object> data) {

        String transportN = data.get("transport").toString();
        double distance = Double.parseDouble(data.get("distance").toString());
        String infrastructure = data.get("infrastructure").toString();
        String remplissage = data.get("remplissage").toString();

        MoyenTransport transport = transportRepo.findByNom(transportN)
                .orElseThrow(() -> new RuntimeException("Transport inconnu"));

        double facteurEmission = transport.getFacteurEmission();

        double coeffInfra = infraRepo
                .findByTransportAndTypeInfrastructure(transport, infrastructure)
                .map(CoefficientInfrastructure::getCoefficient)
                .orElse(1.0);

        double coeffRemplissage = remplissageRepo
                .findByTransportAndNiveauRemplissage(transport, remplissage)
                .map(CoefficientRemplissage::getCoefficient)
                .orElse(1.0);



        double empreinte = distance * facteurEmission * coeffInfra * coeffRemplissage;
        System.out.println("=== DETAIL DU CALCUL ===");
        System.out.println("Transport : " + transportN);
        System.out.println("Distance : " + distance);
        System.out.println("Facteur émission : " + facteurEmission);
        System.out.println("Coeff infrastructure : " + coeffInfra);
        System.out.println("Coeff remplissage : " + coeffRemplissage);
        System.out.println("Empreinte final : " + empreinte);

        //afin de comprarer avec un autre moyen de transport aléatoirement
        MoyenTransport autreTransport = transportRepo.findAll().stream()
                .filter(t -> !t.getNom().equals(transportN))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Aucun autre transport disponible"));

        double facteurEmissionAutre = autreTransport.getFacteurEmission();
        double coeffInfraAutre = infraRepo.findByTransportAndTypeInfrastructure(autreTransport, infrastructure)
                .map(CoefficientInfrastructure::getCoefficient)
                .orElse(1.0);
        double coeffRemplissageAutre = remplissageRepo.findByTransportAndNiveauRemplissage(autreTransport, remplissage)
                .map(CoefficientRemplissage::getCoefficient)
                .orElse(1.0);

        double empreinteAutre = distance * facteurEmissionAutre * coeffInfraAutre * coeffRemplissageAutre;

        double difference = Math.abs(empreinte - empreinteAutre);

        //pour faire une sauvegarde sur la bdd
        EmpreinteCarbone empreinteEntity = new EmpreinteCarbone();
        empreinteEntity.setDistanceKm(distance);
        empreinteEntity.setTransport(transport);
        empreinteRepo.save(empreinteEntity);


        return Map.of(
                "transport", transportN,
                "distance", distance,
                "facteur", facteurEmission,
                "coeffInfrastructure", coeffInfra,
                "coeffRemplissage", coeffRemplissage,
                "empreinte", empreinte ,
                "autreTransport", autreTransport.getNom(),
                "empreinteAutre", empreinteAutre,
                "difference", difference
        );
    }
}
