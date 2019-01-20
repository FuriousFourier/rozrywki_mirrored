package pl.edu.agh.reporting.reports.domain.site;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import pl.edu.agh.reporting.jackson.MoneySerializer;

import java.util.Map;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class BetSlipsInfo {

    @JsonSerialize(using = MoneySerializer.class)
    @Getter
    private Money overallStakes;

    @Getter
    private int betSlipsCount;

    private final Map<String, BetSlipInfo> betSlipsWithStakes;

    BetSlipsInfo(CurrencyUnit currencyUnit) {
        overallStakes = Money.zero(currencyUnit);
        betSlipsCount = 0;
        betSlipsWithStakes = Maps.newHashMap();
    }

    void addBetSlip(BetSlipInfo betSlipInfo) {
        this.betSlipsWithStakes.put(betSlipInfo.getBetSlipId(), betSlipInfo);
        this.overallStakes = overallStakes.plus(betSlipInfo.getStake());
        this.betSlipsCount++;
    }

    BetSlipInfo removeBetSlip(String betSlipId) {
        final BetSlipInfo betSlipInfo = this.betSlipsWithStakes.remove(betSlipId);
        this.overallStakes = overallStakes.minus(betSlipInfo.getStake());
        this.betSlipsCount--;
        return betSlipInfo;
    }

}
