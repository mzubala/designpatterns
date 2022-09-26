package pl.com.bottega.designpatterns.sodamachine;

import pl.com.bottega.designpatterns.ecom.Money;

import java.math.BigDecimal;

interface Ledger {

    void dispense(Coin coin);

    Integer getCoinsCount(Coin coin);
}

enum Coin {
    ONE(new Money(new BigDecimal("0.1"))),
    TWO(new Money(new BigDecimal("0.2"))),
    THREE(new Money(new BigDecimal("0.5"))),
    FOUR(new Money(new BigDecimal(1))),
    FIVE(new Money(new BigDecimal(2))),
    SIX(new Money(new BigDecimal(5)));

    Money value;

    Coin(Money value) {
        this.value = value;
    }
}
