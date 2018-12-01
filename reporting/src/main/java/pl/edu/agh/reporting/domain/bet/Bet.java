package pl.edu.agh.reporting.domain.bet;

import lombok.Getter;

@Getter
public class Bet {

    private final String betId;

    private final String event;

    private final String player;

    private BetStatus status;

    public Bet(String betId, String event, String player) {
        this.betId = betId;
        this.event = event;
        this.player = player;
        this.status = BetStatus.OPEN;
    }
}
