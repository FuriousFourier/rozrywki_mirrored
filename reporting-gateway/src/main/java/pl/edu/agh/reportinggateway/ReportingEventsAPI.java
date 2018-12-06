package pl.edu.agh.reportinggateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.events.*;
import pl.edu.agh.reportinggateway.broker.ReportingBrokerClient;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.edu.agh.reporting.ReportingEventType.*;

@RestController
@RequestMapping("/events")
@Slf4j
public class ReportingEventsAPI {

    private final ReportingBrokerClient reportingClient;

    public ReportingEventsAPI(ReportingBrokerClient reportingClient) {
        this.reportingClient = reportingClient;
    }

    @PostMapping(value = "/bet-slip-placed", consumes = APPLICATION_JSON_VALUE)
    public void onBetSlipPlaced(@RequestBody BetSlipPlaced event) {
        log.info("Received event {}", event);

        reportingClient.sendEvent(event, BET_SLIP_PLACED);

        log.info("Forwarded event {}", event);
    }

    @PostMapping(value = "/bet-slip-gone-live", consumes = APPLICATION_JSON_VALUE)
    public void onBetSlipGoneLive(@RequestBody BetSlipGoneLive event) {
        log.info("Received event {}", event);

        reportingClient.sendEvent(event, BET_SLIP_GONE_LIVE);

        log.info("Forwarded event {}", event);
    }

    @PostMapping(value = "/bet-slip-finished", consumes = APPLICATION_JSON_VALUE)
    public void onBetSlipFinished(@RequestBody BetSlipFinished event) {
        log.info("Received event {}", event);

        reportingClient.sendEvent(event, BET_SLIP_FINISHED);

        log.info("Forwarded event {}", event);
    }

    @PostMapping(value = "/pay-in-credited", consumes = APPLICATION_JSON_VALUE)
    public void onPayInCredited(@RequestBody PayInCredited event) {
        log.info("Received event {}", event);

        reportingClient.sendEvent(event, PAY_IN_CREDITED);

        log.info("Forwarded event {}", event);
    }

    @PostMapping(value = "/pay-out-claimed", consumes = APPLICATION_JSON_VALUE)
    public void onPayOutClaimed(@RequestBody PayOutClaimed event) {
        log.info("Received event {}", event);

        reportingClient.sendEvent(event, PAY_OUT_CLAIMED);

        log.info("Forwarded event {}", event);
    }

}
