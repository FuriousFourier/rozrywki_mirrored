package pl.edu.agh.reporting.reports.application.handlers;

import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.BetSlipPlaced;
import pl.edu.agh.reporting.reports.application.ReportingEventHandler;
import pl.edu.agh.reporting.reports.domain.bet.Bet;
import pl.edu.agh.reporting.reports.domain.bet.BetRepository;
import pl.edu.agh.reporting.reports.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.reports.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.reports.domain.site.SiteReport;
import pl.edu.agh.reporting.reports.domain.site.SiteReportRepository;

@Component
class BetSlipPlacedHandler implements ReportingEventHandler<BetSlipPlaced> {

    private final BetRepository betRepository;

    private final OnlinePlayersRepository onlinePlayersRepository;

    private final SiteReportRepository siteReportRepository;

    BetSlipPlacedHandler(BetRepository betRepository, OnlinePlayersRepository onlinePlayersRepository, SiteReportRepository siteReportRepository) {
        this.betRepository = betRepository;
        this.onlinePlayersRepository = onlinePlayersRepository;
        this.siteReportRepository = siteReportRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.BET_SLIP_PLACED;
    }

    @Override
    public void handle(BetSlipPlaced event) {
        createBet(event);
        if (event.getOnlineChannelName() != null) {
            modifyPlayersReport(event);
        }
        if (event.getSiteName() != null) {
            modifySiteReport(event);
        }
    }

    private void modifyPlayersReport(BetSlipPlaced event) {
        final String playerName = event.getPlayerName();
        final CurrencyUnit playersCurrency = event.getStake().getCurrencyUnit();
        final OnlinePlayerReport playerFromRepository = onlinePlayersRepository.findByName(playerName)
                .orElse(OnlinePlayerReport.createEmpty(playerName, playersCurrency));
        playerFromRepository.apply(event);
        onlinePlayersRepository.save(playerFromRepository);
    }

    private void modifySiteReport(BetSlipPlaced event) {
        final String siteName = event.getSiteName();
        final CurrencyUnit playersCurrency = event.getStake().getCurrencyUnit();
        final SiteReport siteReportFromRepository = siteReportRepository.findBySiteName(siteName)
                .orElse(SiteReport.createEmpty(siteName, playersCurrency));
        siteReportFromRepository.apply(event);
        siteReportRepository.save(siteReportFromRepository);
    }

    private void createBet(BetSlipPlaced event) {
        final Bet bet = Bet.builder()
                .betId(event.getBetSlipId())
                .player(event.getPlayerName())
                .betCount(event.getBetCount())
                .stakes(event.getStake())
                .build();
        betRepository.save(bet);
    }
}
