package pl.edu.agh.reporting.application;

import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.ReportingEvent;

public interface ReportingEventHandler {

    ReportingEventType handledType();

    void handle(ReportingEvent event);

}
