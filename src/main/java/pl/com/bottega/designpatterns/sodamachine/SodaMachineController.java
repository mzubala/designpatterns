package pl.com.bottega.designpatterns.sodamachine;

import pl.com.bottega.designpatterns.ecom.Money;

import java.util.LinkedList;
import java.util.List;

class SodaMachineController {

    private final Display display;
    private final Ledger ledger;

    private final DrinkDispenser drinkDispenser;

    // TODO declare state here

    // TODO get rid of the below fields - move them to the state classes
    private List<Coin> insertedCoins = new LinkedList<>();
    private Money insertedCoinsValue = Money.zero();

    private Boolean dispensing = false;

    SodaMachineController(Display display, Ledger ledger, DrinkDispenser dispenser) {
        this.display = display;
        this.ledger = ledger;
        this.drinkDispenser = dispenser;
        this.display.show("Dzień dobry! Zawsze Coca Cola!");
    }

    void switchState(SodaMachineState newState) {
        // TODO assign the new state
    }

    // TODO the below methods should delegate to the state object
    //  TODO their current content should be moved to proper state classes and state switching should also be added
    void coinInserted(Coin coin) {
        if (dispensing) {
            ledger.dispense(coin);
        }
        insertedCoins.add(coin);
        insertedCoinsValue = insertedCoinsValue.add(coin.value);
        this.display.show(String.format("Kwota: %s", format(insertedCoinsValue)));
    }

    void cancelRequested() {
        if (dispensing) {
            return;
        }
        insertedCoins.forEach(ledger::dispense);
        insertedCoins.clear();
        insertedCoinsValue = Money.zero();
        display.show("Dzień dobry! Zawsze Coca Cola!");
    }

    void drinkRequested(Drink drink) {
        if (insertedCoins.size() > 0) {
            if (insertedCoinsValue.compareTo(drink.price) >= 0) {
                dispenseChange(drink);
                drinkDispenser.dispense(drink);
                dispensing = true;
                display.show("Wydawanie. Proszę czekać...");
            } else {
                this.display.showBriefly(String.format("Brakuje %s", format(drink.price.sub(insertedCoinsValue))));
            }
        } else {
            var price = drink.price;
            var formattedMoney = format(price);
            this.display.showBriefly(String.format("%s %s", drink.name(), formattedMoney));
        }
    }

    void drinkDispensed() {
        insertedCoins.clear();
        insertedCoinsValue = Money.zero();
        dispensing = false;
        display.showBriefly("Odbierz napój. Dziękuję!");
        display.show("Dzień dobry! Zawsze Coca Cola!");
    }

    private static String format(Money price) {
        return String.format("%s %s", price.value().toString(), price.currency().getCurrencyCode());
    }

    private void dispenseChange(Drink drink) {
        var change = insertedCoinsValue.sub(drink.price);
        for (int i = Coin.values().length - 1; i > 0 && change.compareTo(Money.zero()) > 0; ) {
            var coin = Coin.values()[i];
            if (change.compareTo(coin.value) >= 0 && ledger.getCoinsCount(coin) > 0) {
                ledger.dispense(coin);
                change = change.sub(coin.value);
            } else {
                i--;
            }
        }
    }
}
