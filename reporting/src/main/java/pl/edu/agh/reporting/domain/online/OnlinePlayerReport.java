package pl.edu.agh.reporting.domain.online;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import pl.edu.agh.reporting.jackson.MoneySerializer;

@AllArgsConstructor
@Getter
public class OnlinePlayerReport {

    @Id
    private String name;

    private int betSlipCount;

    @JsonSerialize(using = MoneySerializer.class)
    private Money stakesOverall;

    @JsonSerialize(using = MoneySerializer.class)
    private Money refundsOverall;

    @JsonSerialize(using = MoneySerializer.class)
    private Money overallRevenue;

}
