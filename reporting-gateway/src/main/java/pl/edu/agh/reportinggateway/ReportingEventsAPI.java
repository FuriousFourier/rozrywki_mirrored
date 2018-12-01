package pl.edu.agh.reportinggateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.events.BetMadeEvent;
import pl.edu.agh.reportinggateway.broker.ReportingBrokerClient;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.edu.agh.reporting.ReportingEventType.BET_MADE;

@RestController
@RequestMapping("/events")
@Slf4j
public class ReportingEventsAPI {

    private final ReportingBrokerClient reportingClient;

    public ReportingEventsAPI(ReportingBrokerClient reportingClient) {
        this.reportingClient = reportingClient;
    }

    @PostMapping(value = "/betmade", consumes = APPLICATION_JSON_VALUE)
    public void onBetMade(@RequestBody BetMadeEvent betMadeEvent) {
        log.info("Received event {}", betMadeEvent);

        reportingClient.sendEvent(betMadeEvent, BET_MADE);

        log.info("Forwarded event {}", betMadeEvent);
    }

}
