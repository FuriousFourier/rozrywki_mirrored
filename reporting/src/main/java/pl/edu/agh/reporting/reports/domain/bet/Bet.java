package pl.edu.agh.reporting.reports.domain.bet;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import pl.edu.agh.reporting.events.BetSlipFinished;
import pl.edu.agh.reporting.events.BetSlipGoneLive;
import pl.edu.agh.reporting.events.ReportingEvent;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@Builder
@ToString
public class Bet {

    @Id
    private final String betId;

    private final String player;

    private final Money stakes;

    private final int betCount;

    @Builder.Default
    private BetStatus status = BetStatus.OPEN;

    public void handleEvent(ReportingEvent event) {
        if (event instanceof BetSlipFinished) {
            handle((BetSlipFinished) event);
        } else if (event instanceof BetSlipGoneLive) {
            handle((BetSlipGoneLive) event);
        } else {
            throw new IllegalArgumentException("Cannot handle " + event);
        }
    }

    public boolean isLive() {
        return status == BetStatus.LIVE;
    }

    private void handle(BetSlipGoneLive event) {
        checkArgument(event.getBetSlipId().equals(this.betId));
        if (status == BetStatus.OPEN) {
            status = BetStatus.LIVE;
        }
    }

    private void handle(BetSlipFinished event) {
        status = event.isWon() ? BetStatus.WON : BetStatus.LOST;
    }

}
