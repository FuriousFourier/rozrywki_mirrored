package pl.edu.agh.reporting.events;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BetSlipGoneLive implements ReportingEvent {

    private String betSlipId;

}
