package pl.edu.agh.reporting.domain.bet;

import lombok.Builder;
import lombok.Getter;
import org.joda.money.Money;

@Getter
@Builder
public class Bet {

    private final String betId;

    private final String event;

    private final String player;

    private BetStatus status;

    private final Money pricePaid;

    private Bet(String betId, String event, String player, Money pricePaid) {
        this.betId = betId;
        this.event = event;
        this.player = player;
        this.pricePaid = pricePaid;
        this.status = BetStatus.OPEN;
    }
}
