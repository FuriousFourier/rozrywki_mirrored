package pl.edu.agh.reporting.events;

import java.time.Instant;

public interface ReportingEvent {

    Instant getTimestamp();
}
