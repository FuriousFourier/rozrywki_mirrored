package pl.edu.agh.reporting.ui;

import com.google.common.collect.ImmutableList;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.domain.site.SiteReport;

import java.util.List;

@RestController
public class ReportsController {

    private static final Money TEN_USD = Money.of(CurrencyUnit.USD, 10);

    public List<SiteReport> getSiteReports() {
        return null;
    }

    @GetMapping("/api/players")
    public List<OnlinePlayerReport> getOnlinePlayerReports() {
        return ImmutableList.of(
                new OnlinePlayerReport(
                        "Janek",
                        2,
                        TEN_USD,
                        TEN_USD,
                        TEN_USD
                )
        );
    }

}
