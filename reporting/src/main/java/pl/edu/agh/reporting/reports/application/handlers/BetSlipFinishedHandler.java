package pl.edu.agh.reporting.reports.application.handlers;

import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.BetSlipFinished;
import pl.edu.agh.reporting.reports.application.ReportingEventHandler;
import pl.edu.agh.reporting.reports.domain.bet.BetRepository;
import pl.edu.agh.reporting.reports.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.reports.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.reports.domain.site.SiteReport;
import pl.edu.agh.reporting.reports.domain.site.SiteReportRepository;

@Component
class BetSlipFinishedHandler implements ReportingEventHandler<BetSlipFinished> {

    private final OnlinePlayersRepository onlinePlayersRepository;

    private final BetRepository betRepository;

    private final SiteReportRepository siteReportRepository;

    BetSlipFinishedHandler(OnlinePlayersRepository onlinePlayersRepository, BetRepository betRepository, SiteReportRepository siteReportRepository) {
        this.onlinePlayersRepository = onlinePlayersRepository;
        this.betRepository = betRepository;
        this.siteReportRepository = siteReportRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.BET_SLIP_FINISHED;
    }

    @Override
    public void handle(BetSlipFinished event) {
        modifyBet(event);
        if (event.getOnlineChannelName() != null) {
            modifyPlayersReport(event);
        }
        if (event.getSiteName() != null) {
            modifySiteReport(event);
        }
    }

    private void modifyBet(BetSlipFinished event) {
        betRepository.findById(event.getBetSlipId())
                .ifPresent(bet -> {
                    bet.handleEvent(event);
                    betRepository.save(bet);
                });
    }

    private void modifyPlayersReport(BetSlipFinished event) {
        final String playerName = event.getPlayerName();
        final CurrencyUnit playersCurrency = event.getRefund().getCurrencyUnit();
        final OnlinePlayerReport playerFromRepository = onlinePlayersRepository.findByName(playerName)
                .orElse(OnlinePlayerReport.createEmpty(playerName, playersCurrency));
        playerFromRepository.apply(event);
        onlinePlayersRepository.save(playerFromRepository);
    }

    private void modifySiteReport(BetSlipFinished event) {
        final String siteName = event.getSiteName();
        final CurrencyUnit playersCurrency = event.getRevenue().getCurrencyUnit();
        final SiteReport siteReportFromRepository = siteReportRepository.findBySiteName(siteName)
                .orElse(SiteReport.createEmpty(siteName, playersCurrency));
        siteReportFromRepository.apply(event);
        siteReportRepository.save(siteReportFromRepository);
    }

}
