package pl.edu.agh.reporting.reports.application;

import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.ReportingEvent;

public interface ReportingEventHandler<T extends ReportingEvent> {

    ReportingEventType handledType();

    void handle(T event);

}
