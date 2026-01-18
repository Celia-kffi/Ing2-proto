package triplan.back.controllers;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class empreinte_controllers {

    private double getFacteurEmission(String transport) {
        return switch (transport) {
            case "Marche" -> 0.0;
            case "Vélo mécanique" -> 0.0;
            case "Métro" -> 0.05;
            case "Vélo électrique" -> 0.01;
            case "Trottinette électrique" -> 0.02;
            case "Voiture électrique" -> 0.07;
            case "Bus" -> 0.08;
            case "Voiture" -> 0.20;
            case "Avion" -> 0.22;
            default -> 0.0;
        };
    }

    @PostMapping("/calcul")
    public Map<String, Object> calcul(@RequestBody Map<String, Object> data) {

        String transport = data.get("transport").toString();
        double distance = Double.parseDouble(data.get("distance").toString());

        double facteur = getFacteurEmission(transport);
        double empreinte = distance * facteur;

        return Map.of(
                "transport", transport,
                "distance", distance,
                "empreinte", empreinte
        );
    }
}
