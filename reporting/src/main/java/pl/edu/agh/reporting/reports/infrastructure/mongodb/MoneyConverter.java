package pl.edu.agh.reporting.reports.infrastructure.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
final class MoneyConverter {

    private static final String CODE = "currency";
    private static final String AMOUNT = "amount";

    @WritingConverter
    static class Write implements Converter<Money, DBObject> {

        @Override
        public DBObject convert(Money source) {
            final DBObject money = new BasicDBObject();

            money.put(CODE, source.getCurrencyUnit().getCode());
            money.put(AMOUNT, source.getAmount().toString());

            return money;
        }
    }


    @ReadingConverter
    static class Read implements Converter<Document, Money> {

        @Override
        public Money convert(Document money) {
            String code = (String) money.get(CODE);
            String amount = (String) money.get(AMOUNT);

            return Money.of(CurrencyUnit.of(code), new BigDecimal(amount));
        }

    }

}