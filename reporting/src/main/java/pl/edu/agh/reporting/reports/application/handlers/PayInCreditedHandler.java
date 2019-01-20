package pl.edu.agh.reporting.reports.application.handlers;

import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.PayInCredited;
import pl.edu.agh.reporting.reports.application.ReportingEventHandler;
import pl.edu.agh.reporting.reports.domain.site.SiteReport;
import pl.edu.agh.reporting.reports.domain.site.SiteReportRepository;

@Component
class PayInCreditedHandler implements ReportingEventHandler<PayInCredited> {

    private final SiteReportRepository siteReportRepository;

    PayInCreditedHandler(SiteReportRepository siteReportRepository) {
        this.siteReportRepository = siteReportRepository;
    }

    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.PAY_IN_CREDITED;
    }

    @Override
    public void handle(PayInCredited event) {
        modifySiteReport(event);
    }

    private void modifySiteReport(PayInCredited event) {
        final String siteName = event.getSiteName();
        final CurrencyUnit playersCurrency = event.getMoney().getCurrencyUnit();
        final SiteReport siteReportFromRepository = siteReportRepository.findBySiteName(siteName)
                .orElse(SiteReport.createEmpty(siteName, playersCurrency));
        siteReportFromRepository.apply(event);
        siteReportRepository.save(siteReportFromRepository);
    }


}
