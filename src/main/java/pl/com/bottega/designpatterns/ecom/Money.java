package pl.com.bottega.designpatterns.ecom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

import static java.math.BigDecimal.ZERO;

public record Money(BigDecimal value, Currency currency) implements Comparable<Money> {

    public Money(BigDecimal value) {
        this(value, Currency.getInstance(Locale.getDefault()));
    }

    public Money(BigDecimal value, Currency currency) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency;
    }

    public static Money zero() {
        return new Money(ZERO);
    }

    public static Money zero(Currency currency) {
        return new Money(ZERO, currency);
    }

    public Money add(Money other) {
        ensureSameCurrency(other);
        return new Money(value.add(other.value), currency);
    }

    public Money sub(Money other) {
        ensureSameCurrency(other);
        return new Money(value.subtract(other.value), currency);
    }

    public Money times(int n) {
        return new Money(value.multiply(new BigDecimal(n)), currency);
    }

    public Money times(BigDecimal n) {
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
