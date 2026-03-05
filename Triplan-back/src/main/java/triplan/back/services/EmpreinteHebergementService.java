package triplan.back.services;
import org.springframework.stereotype.Service;
import triplan.back.entities.*;
import triplan.back.repositories.HebergementEnergieRepository;
import triplan.back.repositories.EmpreinteHebergementRepository;

@Service
public class EmpreinteHebergementService {

    private final HebergementEnergieRepository energieRepository;
    private final EmpreinteHebergementRepository empreinteRepository;

    public EmpreinteHebergementService(
            HebergementEnergieRepository energieRepository,
            EmpreinteHebergementRepository empreinteRepository
    ) {
        this.energieRepository = energieRepository;
        this.empreinteRepository = empreinteRepository;
    }

    public double calculerEmpreinte(
            Hebergements hebergement,
            int duree,
            Saison saison,
            boolean chauffageActive
    ) {

        HebergementEnergie energie = energieRepository
                .findByHebergement(hebergement)
                .orElseThrow(() -> new RuntimeException("Données énergétiques introuvables"));

        double consoTotale;

        if (hebergement.getType().equalsIgnoreCase("APPARTEMENT")) {
            consoTotale = calculAppartement(energie, duree, saison, chauffageActive);
        } else {
            consoTotale = calculHotel(energie, duree, saison, chauffageActive);
        }

        return consoTotale;
    }

    private double calculAppartement(
            HebergementEnergie energie,
            int duree,
            Saison saison,
            boolean chauffageActive
    ) {

        double surface = energie.getSurfaceM2();

        double consoBase = surface * duree * energie.getConsoBaseKwhM2Jour();

        double consoChauffage = 0;

        if (chauffageActive && energie.getTypeChauffage() != null) {
            consoChauffage = surface * duree *
                    energie.getTypeChauffage().getCoefKwh();
        }

        double conso = consoBase + consoChauffage;

        conso *= getCoefficientSaison(saison);

        return conso * energie.getTypeChauffage().getFacteurCO2();
    }

    private double calculHotel(
            HebergementEnergie energie,
            int duree,
            Saison saison,
            boolean chauffageActive
    ) {

        double consoBase = duree * energie.getConsoHotelKwhNuit();

        double consoChauffage = 0;

        if (chauffageActive && energie.getTypeChauffage() != null) {
            consoChauffage = duree * energie.getTypeChauffage().getCoefKwh();
        }

        double consoTotale = consoBase + consoChauffage;

        consoTotale *= getCoefficientSaison(saison);

        if (energie.getTypeChauffage() != null) {
            return consoTotale * energie.getTypeChauffage().getFacteurCO2();
        }

        return consoTotale;
    }

    private double getCoefficientSaison(Saison saison) {

        switch (saison) {
            case HIVER:
                return 1.3;
            case ETE:
                return 0.9;
            case AUTOMNE:
                return 1.1;
            default:
                return 1.0;
        }
    }
}
