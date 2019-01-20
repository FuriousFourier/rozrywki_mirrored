package pl.edu.agh.reporting.reports.application.handlers;

import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.BetSlipGoneLive;
import pl.edu.agh.reporting.reports.application.ReportingEventHandler;
import pl.edu.agh.reporting.reports.domain.bet.BetRepository;

@Component
class BetSlipGoneLiveHandler implements ReportingEventHandler<BetSlipGoneLive> {

    private final BetRepository betRepository;

    BetSlipGoneLiveHandler(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.BET_SLIP_GONE_LIVE;
    }

    @Override
    public void handle(BetSlipGoneLive event) {
        modifyBet(event);
    }

    private void modifyBet(BetSlipGoneLive event) {
        betRepository.findById(event.getBetSlipId())
                .ifPresent(bet -> {
                    bet.handleEvent(event);
                    betRepository.save(bet);
                });
    }

}
