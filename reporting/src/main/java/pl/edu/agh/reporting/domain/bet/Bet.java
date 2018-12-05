package pl.edu.agh.reporting.domain.bet;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Bet {

    private final String betId;

    private final String event;

    private final String player;

    @Builder.Default
    private BetStatus status = BetStatus.OPEN;

}
