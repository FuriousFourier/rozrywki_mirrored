package pl.edu.agh.reporting.reports.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.reports.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.reports.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.reports.domain.site.SiteReport;
import pl.edu.agh.reporting.reports.domain.site.SiteReportRepository;

import java.time.Instant;
import java.util.List;

@RestController
public class ReportsController {

    private final OnlinePlayersRepository onlinePlayersRepository;
    private final SiteReportRepository siteReportRepository;

    public ReportsController(OnlinePlayersRepository onlinePlayersRepository, SiteReportRepository siteReportRepository) {
        this.onlinePlayersRepository = onlinePlayersRepository;
        this.siteReportRepository = siteReportRepository;
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

    @GetMapping(value = "/api/sites/{start}/{end}")
    public List<SiteReport> getSiteReports(@PathVariable String start, @PathVariable String end) {
        final Instant startTime = Instant.parse(start);
        final Instant endTime = Instant.parse(end);
        return siteReportRepository.findAllBetween(startTime, endTime);
    }

    @GetMapping("/api/sites")
    public List<SiteReport> getSiteReports() {
        return siteReportRepository.findAll();
    }

}
