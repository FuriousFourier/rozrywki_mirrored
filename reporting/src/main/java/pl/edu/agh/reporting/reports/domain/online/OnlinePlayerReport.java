package pl.edu.agh.reporting.reports.domain.online;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import pl.edu.agh.reporting.events.BetSlipFinished;
import pl.edu.agh.reporting.events.BetSlipPlaced;
import pl.edu.agh.reporting.events.ReportingEvent;
import pl.edu.agh.reporting.jackson.MoneySerializer;
import pl.edu.agh.reporting.reports.domain.Aggregate;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class OnlinePlayerReport extends Aggregate {

    private final String name;

    private int betSlipCount;

    private String channel;

    @JsonSerialize(using = MoneySerializer.class)
    private Money stakesOverall;

    @JsonSerialize(using = MoneySerializer.class)
    private Money refundsOverall;

    @JsonSerialize(using = MoneySerializer.class)
    private Money overallRevenue;

    public static OnlinePlayerReport createEmpty(String playerName, CurrencyUnit currency) {
        final Money zeroMoney = Money.zero(currency);
        return new OnlinePlayerReport(playerName, 0, "", zeroMoney, zeroMoney, zeroMoney);
    }

    public static OnlinePlayerReport recreate(String name, CurrencyUnit usd, List<ReportingEvent> events) {
        final OnlinePlayerReport report = createEmpty(name, usd);
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
        } else {
            throw new IllegalArgumentException("Cannot handle " + event);
        }
    }

    private void handle(BetSlipPlaced event) {
        checkState(event.getPlayerName().equals(this.name));
        checkNotNull(event.getOnlineChannelName());

        betSlipCount++;
        stakesOverall = stakesOverall.plus(event.getStake());
        channel = event.getOnlineChannelName();
    }

    private void handle(BetSlipFinished event) {
        checkState(event.getPlayerName().equals(this.name));
        checkNotNull(event.getOnlineChannelName());

        refundsOverall = refundsOverall.plus(event.getRefund());
        overallRevenue = overallRevenue.plus(event.getRevenue());
        channel = event.getOnlineChannelName();
    }
}
