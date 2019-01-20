package pl.edu.agh.reporting.reports.infrastructure.mongodb;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import pl.edu.agh.reporting.events.ReportingEvent;
import pl.edu.agh.reporting.reports.domain.Aggregate;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersistentEvent {

    @Id
    private String id;

    private Instant timestamp;

    private ReportingEvent payload;

    private String aggregateId;

    private String aggregateType;

    public PersistentEvent(String aggregateId, ReportingEvent event,
                           Class<? extends Aggregate> aggregateType) {
        this.id = UUID.randomUUID().toString();
        this.payload = event;
        this.timestamp = event.getTimestamp();
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType.getSimpleName();
    }


}
