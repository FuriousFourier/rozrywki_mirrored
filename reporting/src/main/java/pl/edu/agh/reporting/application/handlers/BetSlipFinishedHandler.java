package pl.edu.agh.reporting.application.handlers;

import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.application.ReportingEventHandler;
import pl.edu.agh.reporting.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.events.BetSlipFinished;

@Component
class BetSlipFinishedHandler implements ReportingEventHandler<BetSlipFinished> {

    private final OnlinePlayersRepository onlinePlayersRepository;

    BetSlipFinishedHandler(OnlinePlayersRepository onlinePlayersRepository) {
        this.onlinePlayersRepository = onlinePlayersRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.BET_SLIP_FINISHED;
    }

    @Override
    public void handle(BetSlipFinished event) {
        modifyPlayersReport(event);
    }

    private void modifyPlayersReport(BetSlipFinished event) {
        final String playerName = event.getPlayerName();
        final CurrencyUnit playersCurrency = event.getRefund().getCurrencyUnit();
        final OnlinePlayerReport playerFromRepository = onlinePlayersRepository.findByName(playerName)
                .orElse(OnlinePlayerReport.createEmpty(playerName, playersCurrency));
        playerFromRepository.apply(event);
        onlinePlayersRepository.save(playerFromRepository);
    }

}
