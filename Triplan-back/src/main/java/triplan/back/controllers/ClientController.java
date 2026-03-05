package triplan.back.controllers;
import org.springframework.web.bind.annotation.*;
import triplan.back.dto.Client;
import triplan.back.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/mock")
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients/{nombre}")
    public List<Client> genererClients(@PathVariable int nombre) {
        return clientService.genererClients(nombre);
    }
}