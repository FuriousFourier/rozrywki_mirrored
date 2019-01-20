package pl.edu.agh.reporting.reports.application;

import org.springframework.stereotype.Service;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.ReportingEvent;

import java.util.Collection;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class ReportingApplicationService {

    private final Map<ReportingEventType, ReportingEventHandler> handlers;

    public ReportingApplicationService(Collection<ReportingEventHandler> handlers) {
        this.handlers = handlers.stream()
                .collect(toMap(ReportingEventHandler::handledType, identity()));
    }

    public void handle(ReportingEvent event, ReportingEventType type) {
        handlers.get(type).handle(event);
    }

}
