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
public class BetSlipFinished implements ReportingEvent {

    private String betSlipId;

    private String playerName;

    private boolean won;

    private Instant timestamp;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money revenue;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money refund;

}
