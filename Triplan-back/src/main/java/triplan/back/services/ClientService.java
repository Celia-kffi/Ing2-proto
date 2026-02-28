package triplan.back.services;

import org.springframework.stereotype.Service;
import triplan.back.dto.Client;
import triplan.back.entities.ClientEntities;
import triplan.back.repositories.ClientRepository;
import java.util.*;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final Random random = new Random();

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private final List<String> prenoms = List.of(
            "Emma", "Lucas", "Sofia", "Adam", "Lina",
            "Yanis", "Noah", "Ines", "Sarah", "Leo"
    );

    private final List<String> noms = List.of(
            "Martin", "El kenz", "Idrissi", "Kouffi", "Robert",
            "Petit", "Durand", "Leroy", "Moreau", "Simon"
    );

    private final List<String> nationalites = List.of(
            "Française", "Espagnole", "Italienne",
            "Algérienne", "Marocaine", "Canadienne"
    );

    private final List<String> voyages = List.of(
            "Paris", "Lyon", "Marseille", "Bordeaux",
            "Nice", "Toulouse" ,"Strasbourg", "lille"
    );

    public List<Client> genererClients(int nombre) {

        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < nombre; i++) {

            String prenom = prenoms.get(random.nextInt(prenoms.size()));
            String nom = noms.get(random.nextInt(noms.size()));
            int age = 18 + random.nextInt(70);
            String nationalite = nationalites.get(random.nextInt(nationalites.size()));
            String voyage = voyages.get(random.nextInt(voyages.size()));
            boolean enFamille = random.nextBoolean();


            Client client = new Client(
                    prenom, nom, age,
                    nationalite, voyage,
                    enFamille
            );

            clients.add(client);

            ClientEntities entity = new ClientEntities(
                    prenom, nom, age,
                    nationalite, voyage,
                    enFamille
            );

            clientRepository.save(entity);
        }

        return clients;
    }
}