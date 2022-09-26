package pl.com.bottega.designpatterns.sodamachine;

import pl.com.bottega.designpatterns.ecom.Money;

import java.util.LinkedList;
import java.util.List;

class SodaMachineController {

    private final Display display;
    private final Ledger ledger;

    private final DrinkDispenser drinkDispenser;

    private SodaMachineState state;

    private Boolean dispensing = false;

    SodaMachineController(Display display, Ledger ledger, DrinkDispenser dispenser) {
        this.display = display;
        this.ledger = ledger;
        this.drinkDispenser = dispenser;
        this.state = new ReadySodaMachine(this);
    }

    void switchState(SodaMachineState newState) {
        state = newState;
    }

    void coinInserted(Coin coin) {
        state.coinInserted(coin);
    }

    void cancelRequested() {
        state.cancelRequested();
    }

    void drinkRequested(Drink drink) {
        state.drinkRequested(drink);
    }

    void drinkDispensed() {
        state.drinkDispensed();
    }

    void show(String message) {
        display.show(message);
    }

    void dispense(Coin coin) {
        ledger.dispense(coin);
    }

    void showBriefly(String message) {
        display.showBriefly(message);
    }

    String format(Money amount) {
        return String.format("%s %s", amount.value().toString(), amount.currency().getCurrencyCode());
    }

    Integer getCoinsCount(Coin coin) {
        return ledger.getCoinsCount(coin);
    }

    void dispense(Drink drink) {
        drinkDispenser.dispense(drink);
    }
}
