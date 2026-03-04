package triplan.back.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import triplan.back.entities.Profil;
import triplan.back.entities.Voyage;
import triplan.back.repositories.ProfilRepository;
import triplan.back.repositories.VoyageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ProfilRepository profilRepository) {
        return args -> {

            if (profilRepository.count() > 0) {
                return;
            }

            Random random = new Random();

            String[] noms = {"Durand","Martin","Bernard","Robert","Petit"};
            String[] prenoms = {"Ines","Noah","Adam","Lina","Emma"};
            String[] types = {"nature","aventure","culture","detente","luxe"};

            for (int i = 1; i <= 500; i++) {

                String nom = noms[random.nextInt(noms.length)];
                String prenom = prenoms[random.nextInt(prenoms.length)];
                String email = "user" + i + "@triplan.com";

                int budget = 500 + random.nextInt(2000);
                int duree = 3 + random.nextInt(10);
                String type = types[random.nextInt(types.length)];

                Profil profil = new Profil(
                        nom,
                        prenom,
                        email,
                        "password",
                        budget,
                        duree,
                        type
                );

                profil.setDateCreation(LocalDateTime.now().minusDays(random.nextInt(365)));
                System.out.println("Mock lancé");
                profil.setActif(true);
                profil.setRole("USER");

                profilRepository.save(profil);
            }
        };
    }

    @Bean
    CommandLineRunner loadVoyages(VoyageRepository voyageRepository) {
        return args -> {

            if (voyageRepository.count() > 0) {
                return;
            }

            List<Voyage> voyages = List.of(

                    new Voyage("Monaco Yacht", "luxe", "France", 3000, 2500, "Monaco", "mer", "yacht"),
                    new Voyage("Cannes Palace", "luxe", "France", 2000, 1800, "Cannes", "mer", "hotel_5_etoiles"),
                    new Voyage("Saint-Tropez Villa", "luxe", "France", 2500, 2200, "Saint-Tropez", "mer", "villa_privee"),
                    new Voyage("Croisiere Côte d'Azur", "luxe", "France", 2800, 2300, "Nice", "mer", "croisiere_luxe"),
                    new Voyage("Megève Chalet", "luxe", "France", 2100, 1900, "Megève", "montagne", "chalet_premium"),
                    new Voyage("Courchevel Prestige", "luxe", "France", 2300, 2000, "Courchevel", "montagne", "ski_premium"),
                    new Voyage("Paris Palace VIP", "luxe", "France", 1800, 1600, "Paris", "ville", "experience_vip"),

                    new Voyage("Chamonix Ski", "aventure", "France", 900, 1000, "Chamonix", "montagne", "ski"),
                    new Voyage("Briançon Trek", "aventure", "France", 800, 900, "Briançon", "montagne", "trek"),
                    new Voyage("Alpes Alpinisme", "aventure", "France", 950, 1000, "Alpes", "montagne", "alpinisme"),
                    new Voyage("Biarritz Surf", "aventure", "France", 700, 800, "Biarritz", "mer", "surf"),
                    new Voyage("Corse Plongée", "aventure", "France", 850, 900, "Corse", "mer", "plongee"),
                    new Voyage("Ardèche Rafting", "aventure", "France", 750, 800, "Ardèche", "nature", "rafting"),
                    new Voyage("Verdon Canyoning", "aventure", "France", 780, 850, "Verdon", "nature", "canyoning"),

                    new Voyage("Paris Musée", "culture", "France", 500, 600, "Paris", "ville", "musee"),
                    new Voyage("Strasbourg Patrimoine", "culture", "France", 450, 500, "Strasbourg", "ville", "patrimoine"),
                    new Voyage("Lyon Gastronomie", "culture", "France", 600, 700, "Lyon", "ville", "gastronomie"),
                    new Voyage("Bordeaux Route des Vins", "culture", "France", 650, 700, "Bordeaux", "ville", "route_des_vins"),
                    new Voyage("Avignon Festival", "culture", "France", 550, 600, "Avignon", "ville", "festival"),

                    new Voyage("Nice Spa", "detente", "France", 1000, 1100, "Nice", "mer", "spa"),
                    new Voyage("La Rochelle Plage", "detente", "France", 600, 700, "La Rochelle", "mer", "plage"),
                    new Voyage("Bretagne Thalasso", "detente", "France", 900, 1000, "Bretagne", "mer", "thalasso"),
                    new Voyage("Provence Yoga", "detente", "France", 700, 800, "Provence", "campagne", "retraite_yoga"),

                    new Voyage("Annecy Lac", "nature", "France", 600, 700, "Annecy", "lac", "lac"),
                    new Voyage("Auvergne Randonnée", "nature", "France", 500, 600, "Auvergne", "montagne", "randonnee"),
                    new Voyage("Vosges Forêt", "nature", "France", 450, 500, "Vosges", "foret", "nature_sauvage"),
                    new Voyage("Cévennes Parc National", "nature", "France", 550, 600, "Cévennes", "montagne", "parc_national"),
                    new Voyage("Cabane en Forêt", "nature", "France", 400, 450, "Jura", "foret", "cabane")

            );

            voyageRepository.saveAll(voyages);

            System.out.println("Voyages enrichis générés !");
        };
    }
}