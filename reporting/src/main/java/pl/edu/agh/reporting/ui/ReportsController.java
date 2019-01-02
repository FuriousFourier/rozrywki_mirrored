package pl.edu.agh.reporting.ui;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.domain.site.SiteReport;

import java.util.List;

@RestController
public class ReportsController {

    private final OnlinePlayersRepository onlinePlayersRepository;

    private static final Money TEN_USD = Money.of(CurrencyUnit.USD, 10);

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

    @GetMapping("/api/players2")
    public List<OnlinePlayerReport> getOnlinePlayerReports(@RequestBody TimeSlot timeSlot) {
        return onlinePlayersRepository.findAllBetween(timeSlot.getStart(), timeSlot.getEnd());
    }

}
