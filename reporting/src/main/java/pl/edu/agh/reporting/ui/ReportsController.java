package pl.edu.agh.reporting.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.domain.site.SiteReport;

import java.time.Instant;
import java.util.List;

@RestController
public class ReportsController {

    private final OnlinePlayersRepository onlinePlayersRepository;

    public ReportsController(OnlinePlayersRepository onlinePlayersRepository) {
        this.onlinePlayersRepository = onlinePlayersRepository;
    }

    public List<SiteReport> getSiteReports() {
        return null;
    }

    @GetMapping("/api/players")
    public List<OnlinePlayerReport> getOnlinePlayerReports() {
        return onlinePlayersRepository.findAll();
    }

    @GetMapping(value = "/api/players/{start}/{end}")
    public List<OnlinePlayerReport> getOnlinePlayerReports(@PathVariable String start, @PathVariable String end) {
        final Instant startTime = Instant.parse(start);
        final Instant endTime = Instant.parse(end);
        return onlinePlayersRepository.findAllBetween(startTime, endTime);
    }

}
