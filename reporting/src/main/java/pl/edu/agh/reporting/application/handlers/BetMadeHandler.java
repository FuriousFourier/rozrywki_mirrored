package pl.edu.agh.reporting.application.handlers;

import org.springframework.stereotype.Component;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.application.ReportingEventHandler;
import pl.edu.agh.reporting.events.ReportingEvent;

@Component
class BetMadeHandler implements ReportingEventHandler {
    @Override
    public ReportingEventType handledType() {
        return ReportingEventType.BET_MADE;
    }

    @Override
    public void handle(ReportingEvent event) {
        // TODO
    }
}
