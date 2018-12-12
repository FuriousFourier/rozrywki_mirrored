package pl.edu.agh.reporting;

import lombok.Getter;
import pl.edu.agh.reporting.events.*;

public enum ReportingEventType {
    BET_SLIP_FINISHED(BetSlipFinished.class),
    BET_SLIP_GONE_LIVE(BetSlipGoneLive.class),
    BET_SLIP_PLACED(BetSlipPlaced.class),
    PAY_IN_CREDITED(PayInCredited.class),
    PAY_OUT_CLAIMED(PayOutClaimed.class);

    @Getter
    private final Class<? extends ReportingEvent> eventClass;

    ReportingEventType(Class<? extends ReportingEvent> eventClass) {
        this.eventClass = eventClass;
    }
}
