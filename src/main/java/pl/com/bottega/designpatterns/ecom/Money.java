package pl.com.bottega.designpatterns.ecom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

record Money(BigDecimal value, Currency currency) implements Comparable<Money> {

    Money(BigDecimal value) {
        this(value, Currency.getInstance(Locale.getDefault()));
    }

    Money(BigDecimal value, Currency currency) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    Money add(Money other) {
        return null;
    }

    Money times(int n) {
        return null;
    }

    Money times(BigDecimal n) {
        return null;
    }

    @Override
    public int compareTo(Money other) {
        return 0;
    }
}
