package triplan.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import triplan.back.entities.Voyage;
import triplan.back.repositories.VoyageRepository;

import java.util.List;

@Service
public class VoyageService {

    private final VoyageRepository voyageRepository;

    @Autowired
    public VoyageService(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    public List<Voyage> getVoyages() {
        return voyageRepository.findAll();
    }
}
