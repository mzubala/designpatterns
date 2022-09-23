package pl.com.bottega.designpatterns.ecom;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MoneyTest {

    private final Money fiveUsd = new Money(new BigDecimal(5), Currency.getInstance("USD"));
    private final Money tenUsd = new Money(TEN, Currency.getInstance("USD"));
    private final Money fifteenUsd = new Money(new BigDecimal(15), Currency.getInstance("USD"));
    private final Money oneEur = new Money(ONE, Currency.getInstance("EUR"));

    @Test
    void createsMoneyWithDefaultCurrency() {
        assertThat(new Money(TEN)).isEqualTo(new Money(TEN, Currency.getInstance(Locale.getDefault())));
    }

    @Test
    void addsMoney() {
        assertThat(fiveUsd.add(tenUsd)).isEqualTo(fifteenUsd);
        assertThat(fiveUsd.add(fiveUsd)).isEqualTo(tenUsd);
    }

    @Test
    void subtractsMoney() {
        assertThat(tenUsd.sub(fiveUsd)).isEqualTo(fiveUsd);
        assertThat(fifteenUsd.sub(tenUsd)).isEqualTo(fiveUsd);
    }

    @Test
    void multipliesMoneyByANumber() {
        assertThat(tenUsd.times(2)).isEqualTo(new Money(new BigDecimal(20), Currency.getInstance("USD")));
        assertThat(fiveUsd.times(new BigDecimal(3.0))).isEqualTo(fifteenUsd);
        assertThat(fifteenUsd.times(new BigDecimal(0.33333))).isEqualTo(fiveUsd);
    }

    @Test
    void comparesMoney() {
        assertThat(tenUsd.compareTo(fiveUsd)).isGreaterThan(0);
        assertThat(tenUsd.compareTo(fifteenUsd)).isLessThan(0);
        assertThat(tenUsd.compareTo(new Money(TEN, Currency.getInstance("USD")))).isEqualTo(0);
    }

    @Test
    void cannotPerformOperationsOnDifferentCurrencies() {
        assertThatThrownBy(() -> {
            fiveUsd.add(oneEur);
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            fiveUsd.sub(oneEur);
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            fiveUsd.compareTo(oneEur);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}