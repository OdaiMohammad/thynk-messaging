package ae.accumed.thynkrequestsstats.controllers;


import ae.accumed.thynkrequestsstats.Services.ThynkRequestService;
import ae.accumed.thynkrequestsstats.response.StatsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ThynkRequestStatsController {
    private final ThynkRequestService thynkRequestService;

    @Autowired
    public ThynkRequestStatsController(ThynkRequestService thynkRequestService) {
        this.thynkRequestService = thynkRequestService;
    }

    @GetMapping
    String index(Model model) {
        StatsResponse statsResponse =thynkRequestService.getStats();
        model.addAttribute("stats", statsResponse);
        return "index";
    }
}
