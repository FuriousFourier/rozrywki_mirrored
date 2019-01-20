package pl.edu.agh.reporting.reports.domain.site;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import pl.edu.agh.reporting.events.*;
import pl.edu.agh.reporting.jackson.MoneySerializer;
import pl.edu.agh.reporting.reports.domain.Aggregate;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SiteReport extends Aggregate {

    private final String name;

    private BetSlipsInfo openBetSlips;

    private BetSlipsInfo wonBetSlips;

    private BetSlipsInfo lostBetSlips;

    @JsonSerialize(using = MoneySerializer.class)
    private Money paidIn;

    @JsonSerialize(using = MoneySerializer.class)
    private Money paidOut;

    public static SiteReport createEmpty(String siteName, CurrencyUnit currency) {
        final Money zeroMoney = Money.zero(currency);
        return new SiteReport(
                siteName,
                new BetSlipsInfo(currency), new BetSlipsInfo(currency), new BetSlipsInfo(currency),
                zeroMoney, zeroMoney
        );
    }

    public static SiteReport recreate(String name, CurrencyUnit usd, List<ReportingEvent> events) {
        final SiteReport report = createEmpty(name, usd);
        events.forEach(report::handleEvent);
        return report;
    }


    @Override
    public void apply(ReportingEvent event) {
        handleEvent(event);
        super.appendEvent(event);
    }

    private void handleEvent(ReportingEvent event) {
        if (event instanceof BetSlipPlaced) {
            handle((BetSlipPlaced) event);
        } else if (event instanceof BetSlipFinished) {
            handle((BetSlipFinished) event);
        } else if (event instanceof PayOutClaimed) {
            handle((PayOutClaimed) event);
        } else if (event instanceof PayInCredited) {
            handle((PayInCredited) event);
        } else {
            throw new IllegalArgumentException("Cannot handle " + event);
        }
    }

    private void handle(PayInCredited event) {
        paidIn = paidIn.plus(event.getMoney());
    }

    private void handle(PayOutClaimed event) {
        paidOut = paidOut.plus(event.getMoney());
    }

    private void handle(BetSlipFinished event) {
        final BetSlipInfo betSlip = openBetSlips.removeBetSlip(event.getBetSlipId());
        if (event.isWon()) {
            wonBetSlips.addBetSlip(betSlip);
        } else {
            lostBetSlips.addBetSlip(betSlip);
        }
    }

    private void handle(BetSlipPlaced betSlipPlaced) {
        openBetSlips.addBetSlip(new BetSlipInfo(betSlipPlaced.getBetSlipId(), betSlipPlaced.getStake()));
    }


}
