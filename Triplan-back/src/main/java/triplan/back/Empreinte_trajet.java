package triplan.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("triplan.back.entities")
@EnableJpaRepositories("triplan.back.repositories")
public class Empreinte_trajet {

    public static void main(String[] args) {
        SpringApplication.run(Empreinte_trajet.class, args);
    }
}
