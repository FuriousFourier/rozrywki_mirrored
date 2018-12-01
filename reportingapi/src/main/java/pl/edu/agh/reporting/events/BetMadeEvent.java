package pl.edu.agh.reporting.events;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BetMadeEvent extends ReportingEvent {

    private String betId;

}
