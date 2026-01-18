package triplan.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

@SpringBootApplication
public class Empreinte_trajet implements CommandLineRunner {

    @Autowired
    private DataSource dataSource; //

    public static void main(String[] args) {
        SpringApplication.run(Empreinte_trajet.class, args);
    }

    //les données mockées
    static double getFacteurEmission(String transport) {
        return switch (transport) {
            case "Avion" -> 0.22;
            case "Voiture" -> 0.20;
            case "TGV" -> 0.00;
            default -> 0.0;
        };
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le moyen de transport (Avion, Voiture, TGV) : ");
        String transport = scanner.nextLine();
        System.out.print("Entrez la distance en km : ");
        double distance = scanner.nextDouble();
        double facteur = getFacteurEmission(transport);
        double empreinte = distance * facteur;

        System.out.println("Calcul empreinte carbone:");
        System.out.println("Transport : " + transport);
        System.out.println("Distance : " + distance + " km");
        System.out.println("Empreinte : " + empreinte + " kgCO2");

        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                INSERT INTO empreinte_carbone_trajet
                (moyen_transport, distance_km, facteur_emission, empreinte_kgco2)
                VALUES (?, ?, ?, ?)
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, transport);
            ps.setDouble(2, distance);
            ps.setDouble(3, facteur);
            ps.setDouble(4, empreinte);

            ps.executeUpdate();

            System.out.println("Donnée enregistrée en base");

            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM empreinte_carbone_trajet");

            System.out.println("\nContenu de la table :");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("moyen_transport") + " | " +
                                rs.getDouble("distance_km") + " km | " +
                                rs.getDouble("empreinte_kgco2") + " kgCO2"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
