package pl.com.bottega.designpatterns.ecom;

import java.math.BigDecimal;
import java.util.Currency;

record Money(BigDecimal value, Currency currency) implements Comparable<Money> {

    Money add() {
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
