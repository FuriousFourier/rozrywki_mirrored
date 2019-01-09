package pl.edu.agh.reporting.infrastructure.mongodb.online;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import pl.edu.agh.reporting.events.ReportingEvent;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class OnlinePlayerEvent {

    @Id
    private String id;

    private String name;

    private Instant timestamp;

    private ReportingEvent payload;

    OnlinePlayerEvent(String name, ReportingEvent event) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.payload = event;
        this.timestamp = event.getTimestamp();
    }

}
