package pl.edu.agh.reporting.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;
import org.joda.money.Money;
import pl.edu.agh.reporting.jackson.MoneyDeserializer;
import pl.edu.agh.reporting.jackson.MoneySerializer;

@ToString
@Getter
public class BetSlipFinished {

    private String betSlipId;

    private boolean won;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money revenue;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money refund;

}
