package triplan.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import triplan.back.entities.Voyage;
import triplan.back.services.RecommendationService;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<Voyage> getRecommendations(
            @RequestParam(defaultValue = "3") int limit
    ) {
        return recommendationService.recommend(limit);
    }
}
