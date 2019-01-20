package pl.edu.agh.reporting.reports.domain.site;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.money.Money;

@AllArgsConstructor
@Getter
class BetSlipInfo {

    private final String betSlipId;

    private final Money stake;

}
