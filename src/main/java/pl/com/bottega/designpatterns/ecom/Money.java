package pl.com.bottega.designpatterns.ecom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

import static java.math.BigDecimal.ZERO;

record Money(BigDecimal value, Currency currency) implements Comparable<Money> {

    Money(BigDecimal value) {
        this(value, Currency.getInstance(Locale.getDefault()));
    }

    Money(BigDecimal value, Currency currency) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    static Money zero() {
        return new Money(ZERO);
    }

    static Money zero(Currency currency) {
        return new Money(ZERO, currency);
    }

    Money add(Money other) {
        ensureSameCurrency(other);
        return new Money(value.add(other.value), currency);
    }

    Money sub(Money other) {
        ensureSameCurrency(other);
        return new Money(value.subtract(other.value), currency);
    }

    Money times(int n) {
        return new Money(value.multiply(new BigDecimal(n)), currency);
    }

    Money times(BigDecimal n) {
        return new Money(value.multiply(n), currency);
    }

    @Override
    public int compareTo(Money other) {
        ensureSameCurrency(other);
        return value.compareTo(other.value);
    }

    private void ensureSameCurrency(Money other) {
        if(!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Amounts in different currencies");
        }
    }
}
