package pl.com.bottega.designpatterns.sodamachine;

import pl.com.bottega.designpatterns.ecom.Money;

import java.util.LinkedList;
import java.util.List;

abstract sealed class SodaMachineState {

    protected final SodaMachineController controller;

    SodaMachineState(SodaMachineController controller) {
        this.controller = controller;
    }

    abstract void coinInserted(Coin coin);

    abstract void cancelRequested();

    abstract void drinkRequested(Drink drink);

    abstract void drinkDispensed();
}

final class ReadySodaMachine extends SodaMachineState {

    ReadySodaMachine(SodaMachineController controller) {
        super(controller);
        controller.show("Dzień dobry! Zawsze Coca Cola!");
    }

    @Override
    void coinInserted(Coin coin) {
        controller.switchState(new MachineWithCoinInserted(controller, coin));
    }

    @Override
    void cancelRequested() {

    }

    @Override
    void drinkRequested(Drink drink) {
        var price = drink.price;
        var formattedMoney = controller.format(price);
        controller.showBriefly(String.format("%s %s", drink.name(), formattedMoney));
    }

    @Override
    void drinkDispensed() {

    }
}

final class MachineWithCoinInserted extends SodaMachineState {

    private final List<Coin> insertedCoins = new LinkedList<>();
    private Money insertedCoinsValue = Money.zero();

    MachineWithCoinInserted(SodaMachineController controller, Coin firstCoin) {
        super(controller);
        coinInserted(firstCoin);
    }

    @Override
    void coinInserted(Coin coin) {
        insertedCoins.add(coin);
        insertedCoinsValue = insertedCoinsValue.add(coin.value);
        controller.show(String.format("Kwota: %s", controller.format(insertedCoinsValue)));
    }

    @Override
    void cancelRequested() {
        insertedCoins.forEach(controller::dispense);
        controller.switchState(new ReadySodaMachine(controller));
    }

    @Override
    void drinkRequested(Drink drink) {
        if (insertedCoinsValue.compareTo(drink.price) >= 0) {
            dispenseChange(drink);
            controller.switchState(new DispensingMachine(controller, drink));
        } else {
            controller.showBriefly(String.format("Brakuje %s", controller.format(drink.price.sub(insertedCoinsValue))));
        }
    }

    @Override
    void drinkDispensed() {

    }

    private void dispenseChange(Drink drink) {
        var change = insertedCoinsValue.sub(drink.price);
        for (int i = Coin.values().length - 1; i > 0 && change.compareTo(Money.zero()) > 0; ) {
            var coin = Coin.values()[i];
            if (change.compareTo(coin.value) >= 0 && controller.getCoinsCount(coin) > 0) {
                controller.dispense(coin);
                change = change.sub(coin.value);
            } else {
                i--;
            }
        }
    }
}

final class DispensingMachine extends SodaMachineState {

    DispensingMachine(SodaMachineController controller, Drink drink) {
        super(controller);
        controller.dispense(drink);
        controller.show("Wydawanie. Proszę czekać...");
    }

    @Override
    void coinInserted(Coin coin) {
        controller.dispense(coin);
    }

    @Override
    void cancelRequested() {

    }

    @Override
    void drinkRequested(Drink drink) {

    }

    @Override
    void drinkDispensed() {
        controller.showBriefly("Odbierz napój. Dziękuję!");
        controller.switchState(new ReadySodaMachine(controller));
    }
}