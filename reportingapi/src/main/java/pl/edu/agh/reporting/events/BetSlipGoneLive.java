package pl.edu.agh.reporting.events;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@ToString
@Getter
public class BetSlipGoneLive implements ReportingEvent {

    private String betSlipId;
    private Instant timestamp;

}
