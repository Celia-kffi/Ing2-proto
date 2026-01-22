package triplan.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;
import triplan.back.dto.ActiviteEtape;
import triplan.back.dto.ItineraryRequest;
import triplan.back.dto.ItineraryResponse;
import triplan.back.entities.Activite;
import triplan.back.repositories.ActiviteRepository;
import triplan.back.services.ItineraryService;

@Component
public class OptimalItinerary {

    @Bean
    CommandLineRunner runConsoleApp(ActiviteRepository activiteRepository,
                                    ItineraryService itineraryService) {
        return args -> {
            if (System.console() == null) {
                System.out.println("Pas de terminal interactif: skipping le menu console");
                return;
            }
            Scanner scanner = new Scanner(System.in);

            while (true) {
                afficherMenu();
                String choix = scanner.nextLine();

                switch (choix) {
                    case "1":
                        afficherToutesLesActivites(activiteRepository);
                        break;

                    case "2":
                        calculerItineraire(scanner, activiteRepository, itineraryService);
                        break;

                    case "3":
                        System.out.println("\nAu revoir !\n");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("\nChoix invalide. Reessayez.\n");
                }
            }
        };
    }

    private void afficherMenu() {
        System.out.println("\n========================================");
        System.out.println("  PLANIFICATEUR D'ITINERAIRE");
        System.out.println("========================================");
        System.out.println("\n1. Voir toutes les activites");
        System.out.println("2. Calculer un itineraire optimal");
        System.out.println("3. Quitter");
        System.out.print("\nVotre choix : ");
    }

    private void afficherToutesLesActivites(ActiviteRepository repository) {
        List<Activite> activites = repository.findAll();

        System.out.println("\n========================================");
        System.out.println("      LISTE DES ACTIVITES");
        System.out.println("========================================\n");

        for (Activite act : activites) {
            String icon = getIconForType(act.getTypeActivite());
            System.out.printf("%s [ID: %d] %s\n", icon, act.getId(), act.getNom());
            System.out.printf("   Duree : %d minutes | Type : %s\n",
                    act.getDureeVisiteMinutes(), act.getTypeActivite());
            System.out.println("   ------------------------------------");
        }

        System.out.println("\nAppuyez sur Entree pour continuer...");
        new Scanner(System.in).nextLine();
    }

    private void calculerItineraire(Scanner scanner,
                                    ActiviteRepository repository,
                                    ItineraryService itineraryService) {
        System.out.println("\n========================================");
        System.out.println("   CALCUL D'ITINERAIRE OPTIMAL");
        System.out.println("========================================\n");

        List<Activite> activites = repository.findAll();
        System.out.println("Activites disponibles :\n");

        for (Activite act : activites) {
            String icon = getIconForType(act.getTypeActivite());
            System.out.printf("%s [%d] %s (%d min)\n",
                    icon, act.getId(), act.getNom(), act.getDureeVisiteMinutes());
        }

        System.out.print("\nEntrez les IDs des activites separes par des virgules (ex: 1,2,3) : ");
        String input = scanner.nextLine();

        List<Long> selectedIds = new ArrayList<>();
        try {
            String[] ids = input.split(",");
            for (String id : ids) {
                selectedIds.add(Long.parseLong(id.trim()));
            }
        } catch (NumberFormatException e) {
            System.out.println("\nFormat invalide. Utilisez des chiffres separes par des virgules.\n");
            return;
        }

        if (selectedIds.size() < 2) {
            System.out.println("\nVous devez selectionner au moins 2 activites.\n");
            return;
        }


        System.out.print("\nPoint de depart (ID) ? (Entree pour aucun) : ");
        String departInput = scanner.nextLine().trim();
        Long pointDepart = null;

        if (!departInput.isEmpty()) {
            try {
                pointDepart = Long.parseLong(departInput);
                if (!selectedIds.contains(pointDepart)) {
                    System.out.println("\nLe point de depart doit faire partie des activites selectionnees.\n");
                    pointDepart = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nID invalide, pas de point de depart defini.\n");
            }
        }

        System.out.println("\nCalcul en cours...\n");

        try {
            ItineraryRequest request = new ItineraryRequest();
            request.setActiviteIds(selectedIds);
            request.setPointDepartId(pointDepart);

            ItineraryResponse response = itineraryService.calculerItineraireOptimal(request);

            afficherResultat(response);

        } catch (Exception e) {
            System.out.println("\nErreur : " + e.getMessage() + "\n");
        }

        System.out.println("\nAppuyez sur Entree pour continuer...");
        scanner.nextLine();
    }

    private void afficherResultat(ItineraryResponse response) {
        System.out.println("\n========================================");
        System.out.println("       ITINERAIRE OPTIMAL");
        System.out.println("========================================\n");

        System.out.println("RESUME");
        System.out.println("----------------------------------------");
        System.out.printf("Distance totale    : %.2f km\n", response.getDistanceTotaleKm());
        System.out.printf("Duree totale       : %dh %02dmin\n",
                response.getDureeTotaleMinutes() / 60,
                response.getDureeTotaleMinutes() % 60);


        System.out.println("\nORDRE DE VISITE");
        System.out.println("----------------------------------------\n");

        double distanceCumulee = 0;
        int tempsCumule = 0;

        for (ActiviteEtape etape : response.getItineraire()) {
            String icon = getIconForType(etape.getTypeActivite());

            distanceCumulee += (etape.getDistanceDepuisPrecedenteKm() != null) ? etape.getDistanceDepuisPrecedenteKm() : 0;
            tempsCumule += (etape.getTempsTrajetDepuisPrecedentMinutes() != null) ? etape.getTempsTrajetDepuisPrecedentMinutes() : 0;
            tempsCumule += etape.getDureeVisiteMinutes();

            System.out.printf("%s ETAPE %d : %s\n", icon, etape.getOrdre(), etape.getNom());
            System.out.printf("   Duree de visite : %d minutes\n", etape.getDureeVisiteMinutes());

            if (etape.getDistanceDepuisPrecedenteKm() != null) {
                System.out.printf("Distance depuis l'etape precedente : %.2f km\n",
                        etape.getDistanceDepuisPrecedenteKm());
                System.out.printf("Temps de trajet : %d minutes\n",
                        etape.getTempsTrajetDepuisPrecedentMinutes());
            }

            System.out.printf("Distance cumulée : %.2f km | Temps cumulé : %d min\n", distanceCumulee, tempsCumule);

            System.out.println();
        }

        System.out.println("----------------------------------------");
        System.out.printf("Distance totale confirmée : %.2f km\n", distanceCumulee);
        System.out.printf("Temps total cumulé        : %d min (%dh %02dmin)\n",
                tempsCumule, tempsCumule / 60, tempsCumule % 60);
    }


    private String getIconForType(String type) {
        if (type == null) return "[?]";

        switch (type.toUpperCase()) {
            case "MONUMENT": return "[M]";
            case "MUSEE": return "[A]";
            case "PARC": return "[P]";
            case "QUARTIER": return "[Q]";
            default: return "[?]";
        }
    }
}
