package pl.edu.agh.reporting.application.handlers;

import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.application.ReportingEventHandler;
import pl.edu.agh.reporting.domain.bet.Bet;
import pl.edu.agh.reporting.domain.bet.BetRepository;
import pl.edu.agh.reporting.events.BetMadeEvent;
import pl.edu.agh.reporting.events.ReportingEvent;

@Component
class BetMadeHandler implements ReportingEventHandler {

    private final BetRepository betRepository;

    BetMadeHandler(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.BET_MADE;
    }

    @Override
    public void handle(ReportingEvent event) {
        final BetMadeEvent betMadeEvent = (BetMadeEvent) event;
        final Bet bet = Bet.builder()
                .betId(betMadeEvent.getBetId())
                .event(betMadeEvent.getEventInfo())
                .player(betMadeEvent.getPlayerInfo())
                .pricePaid(betMadeEvent.getBetPrice())
                .build();
        // TODO change reports as well
        betRepository.save(bet);
    }
}
