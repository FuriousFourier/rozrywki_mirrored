package pl.edu.agh.reporting.application.handlers;

import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.application.ReportingEventHandler;
import pl.edu.agh.reporting.domain.bet.Bet;
import pl.edu.agh.reporting.domain.bet.BetRepository;
import pl.edu.agh.reporting.events.BetSlipPlaced;

@Component
class BetMadeHandler implements ReportingEventHandler<BetSlipPlaced> {

    private final BetRepository betRepository;

    BetMadeHandler(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.BET_MADE;
    }

    @Override
    public void handle(BetSlipPlaced event) {
        final Bet bet = Bet.builder()
                .betId(event.getBetSlipId())
                .player(event.getPlayerName())
                .build();
        // TODO change reports as well
        betRepository.save(bet);
    }
}
