package pl.com.bottega.designpatterns.ecom;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.FIFTEEN_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.FIVE_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.ONE_EUR;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.TEN_USD;

class MoneyTest {

    @Test
    void createsMoneyWithDefaultCurrency() {
        assertThat(new Money(TEN)).isEqualTo(new Money(TEN, Currency.getInstance(Locale.getDefault())));
    }

    @Test
    void addsMoney() {
        assertThat(FIVE_USD.add(TEN_USD)).isEqualTo(FIFTEEN_USD);
        assertThat(FIVE_USD.add(FIVE_USD)).isEqualTo(TEN_USD);
    }

    @Test
    void subtractsMoney() {
        assertThat(TEN_USD.sub(FIVE_USD)).isEqualTo(FIVE_USD);
        assertThat(FIFTEEN_USD.sub(TEN_USD)).isEqualTo(FIVE_USD);
    }

    @Test
    void multipliesMoneyByANumber() {
        assertThat(TEN_USD.times(2)).isEqualTo(new Money(new BigDecimal(20), Currency.getInstance("USD")));
        assertThat(FIVE_USD.times(new BigDecimal(3.0))).isEqualTo(FIFTEEN_USD);
        assertThat(FIFTEEN_USD.times(new BigDecimal(0.33333))).isEqualTo(FIVE_USD);
    }

    @Test
    void comparesMoney() {
        assertThat(TEN_USD.compareTo(FIVE_USD)).isGreaterThan(0);
        assertThat(TEN_USD.compareTo(FIFTEEN_USD)).isLessThan(0);
        assertThat(TEN_USD.compareTo(new Money(TEN, Currency.getInstance("USD")))).isEqualTo(0);
    }

    @Test
    void cannotPerformOperationsOnDifferentCurrencies() {
        assertThatThrownBy(() -> {
            FIVE_USD.add(ONE_EUR);
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            FIVE_USD.sub(ONE_EUR);
        }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> {
            FIVE_USD.compareTo(ONE_EUR);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}