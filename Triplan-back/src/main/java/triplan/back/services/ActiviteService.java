package triplan.back.services;

import org.springframework.stereotype.Service;

import triplan.back.entities.Activite;
import triplan.back.entities.FacteurActivite;
import triplan.back.repositories.FacteurActiviteRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActiviteService {

    private final FacteurActiviteRepository facteurRepo;

    public ActiviteService(FacteurActiviteRepository facteurRepo) {
        this.facteurRepo = facteurRepo;
    }

    public Map<String, Object> calculEmpreinteAvecDetails(Activite activite, int nbPersonnes) {

        Map<String, Object> details = new HashMap<>();

        String type = activite.getTypeActivite();

        FacteurActivite facteur = facteurRepo.findByTypeActivite(type)
                .orElseThrow(() -> new RuntimeException("Facteur non trouvé pour " + type));

        double valeur;

        if (facteur.getUnite().equalsIgnoreCase("heure")) {
            double dureeHeures = activite.getDureeVisiteMinutes() / 60.0;
            valeur = dureeHeures * facteur.getFacteurEmission();

            details.put("dureeHeures", dureeHeures);

        } else if (facteur.getUnite().equalsIgnoreCase("personne")) {
            valeur = nbPersonnes * facteur.getFacteurEmission();

        } else {
            valeur = facteur.getFacteurEmission();
        }

        valeur = Math.round(valeur * 100.0) / 100.0;

        details.put("nom", activite.getNom());
        details.put("type", type);
        details.put("ville", activite.getVille());
        details.put("facteur", facteur.getFacteurEmission());
        details.put("unite", facteur.getUnite());
        details.put("empreinte", valeur);

        return details;
    }
}