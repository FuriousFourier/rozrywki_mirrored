package pl.edu.agh.reporting.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;
import org.joda.money.Money;
import pl.edu.agh.reporting.jackson.MoneyDeserializer;
import pl.edu.agh.reporting.jackson.MoneySerializer;

import java.time.Instant;

@ToString
@Getter
public class BetSlipPlaced implements ReportingEvent {

    private String betSlipId;

    private String playerName;

    private String siteName;

    private String onlineChannelName;

    private Instant timestamp;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money stake;

    private int betCount;

}
