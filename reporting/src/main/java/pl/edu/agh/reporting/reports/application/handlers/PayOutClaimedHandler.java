package pl.edu.agh.reporting.reports.application.handlers;

import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.PayOutClaimed;
import pl.edu.agh.reporting.reports.application.ReportingEventHandler;
import pl.edu.agh.reporting.reports.domain.site.SiteReport;
import pl.edu.agh.reporting.reports.domain.site.SiteReportRepository;

@Component
class PayOutClaimedHandler implements ReportingEventHandler<PayOutClaimed> {

    private final SiteReportRepository siteReportRepository;

    PayOutClaimedHandler(SiteReportRepository siteReportRepository) {
        this.siteReportRepository = siteReportRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.PAY_OUT_CLAIMED;
    }

    @Override
    public void handle(PayOutClaimed event) {
        modifySiteReport(event);
    }

    private void modifySiteReport(PayOutClaimed event) {
        final String siteName = event.getSiteName();
        final CurrencyUnit playersCurrency = event.getMoney().getCurrencyUnit();
        final SiteReport siteReportFromRepository = siteReportRepository.findBySiteName(siteName)
                .orElse(SiteReport.createEmpty(siteName, playersCurrency));
        siteReportFromRepository.apply(event);
        siteReportRepository.save(siteReportFromRepository);
    }

}
