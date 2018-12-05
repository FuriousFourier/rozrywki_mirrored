package pl.edu.agh.reporting.domain.bet;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Bet {

    private final String betId;

    private final String event;

    private final String player;

    @Builder.Default
    private BetStatus status = BetStatus.OPEN;

}
