package pl.com.bottega.designpatterns.sodamachine;

import pl.com.bottega.designpatterns.ecom.Money;

import java.math.BigDecimal;

interface DrinkDispenser {
    void dispense(Drink drink);

    Integer getAvailableCount(Drink drink);
}

enum Drink {
    COLA(new Money(new BigDecimal(5))),
    COLA_LIGHT(new Money(new BigDecimal(5.5))),
    FANTA(new Money(new BigDecimal(4.5))),
    SPRITE(new Money(new BigDecimal(4))),
    WATER(new Money(new BigDecimal(3)));

    Money price;
    Drink(Money price) {
        this.price = price;
    }

}
