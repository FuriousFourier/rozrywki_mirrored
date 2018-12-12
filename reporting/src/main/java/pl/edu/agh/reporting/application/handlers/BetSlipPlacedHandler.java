package pl.edu.agh.reporting.application.handlers;

import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.application.ReportingEventHandler;
import pl.edu.agh.reporting.domain.bet.Bet;
import pl.edu.agh.reporting.domain.bet.BetRepository;
import pl.edu.agh.reporting.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.events.BetSlipPlaced;

@Component
class BetSlipPlacedHandler implements ReportingEventHandler<BetSlipPlaced> {

    private final BetRepository betRepository;

    private final OnlinePlayersRepository onlinePlayersRepository;

    BetSlipPlacedHandler(BetRepository betRepository, OnlinePlayersRepository onlinePlayersRepository) {
        this.betRepository = betRepository;
        this.onlinePlayersRepository = onlinePlayersRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.BET_SLIP_PLACED;
    }

    @Override
    public void handle(BetSlipPlaced event) {
        createBet(event);
        modifyPlayersReport(event);
    }

    private void modifyPlayersReport(BetSlipPlaced event) {
        final String playerName = event.getPlayerName();
        final CurrencyUnit playersCurrency = event.getStake().getCurrencyUnit();
        final OnlinePlayerReport playerFromRepository = onlinePlayersRepository.findByName(playerName)
                .orElse(OnlinePlayerReport.createEmpty(playerName, playersCurrency));
        playerFromRepository.apply(event);
        onlinePlayersRepository.save(playerFromRepository);
    }

    private void createBet(BetSlipPlaced event) {
        final Bet bet = Bet.builder()
                .betId(event.getBetSlipId())
                .player(event.getPlayerName())
                .build();
        betRepository.save(bet);
    }
}
