package pl.com.bottega.designpatterns.sodamachine;

import org.junit.jupiter.api.Test;

import static pl.com.bottega.designpatterns.sodamachine.Drink.COLA;
import static pl.com.bottega.designpatterns.sodamachine.Drink.WATER;

class SodaMachineControllerTest {

    private final TestLedger ledger = new TestLedger();
    private final TestDisplay display = new TestDisplay();

    private final TestDrinkDispenser dispenser = new TestDrinkDispenser();
    private final SodaMachineController sut = new SodaMachineController(display, ledger, dispenser);

    @Test
    void printsHelloMessageWhenStarted() {
        // expect
        display.assertLastDisplayedMessage("Dzień dobry! Zawsze Coca Cola!");
    }

    @Test
    void printsDrinkPriceWhenDrinkRequestedWithoutAnyCoinsInserted() {
        // when
        sut.drinkRequested(COLA);

        // expect
        display.assertLastBrieflyDisplayedMessage("COLA 5.00 PLN");
    }

    @Test
    void printsInsertedAmountOfMoney() {
        // when
        sut.coinInserted(Coin.FIVE);

        // then
        display.assertLastDisplayedMessage("Kwota: 2.00 PLN");

        // when
        sut.coinInserted(Coin.SIX);

        // then
        display.assertLastDisplayedMessage("Kwota: 7.00 PLN");
    }

    @Test
    void printsMissingAmountBrieflyWhenNotEnoughCoinsInsertedToDispense() {
        // when
        sut.coinInserted(Coin.ONE);
        sut.drinkRequested(COLA);

        // then
        display.assertLastBrieflyDisplayedMessage("Brakuje 4.90 PLN");
    }

    @Test
    void dispensesDrinkAndShowsInfoAndThankYouMessages() {
        // given
        dispenser.refill(COLA, 1);

        // when
        sut.coinInserted(Coin.SIX);
        sut.drinkRequested(COLA);

        // then
        dispenser.assertLastDispensedDrink(COLA);
        display.assertLastDisplayedMessage("Wydawanie. Proszę czekać...");

        // when
        sut.drinkDispensed();
        display.assertLastBrieflyDisplayedMessage("Odbierz napój. Dziękuję!");
        display.assertLastDisplayedMessage("Dzień dobry! Zawsze Coca Cola!");
    }

    @Test
    void givesBackChange() {
        // given
        dispenser.refill(WATER, 1);
        ledger.fillCoins(Coin.FOUR, 2);

        // when
        sut.coinInserted(Coin.SIX);
        sut.drinkRequested(WATER);

        // then
        dispenser.assertLastDispensedDrink(WATER);
        ledger.assertCoinDispensed(Coin.FOUR, 2);
    }

    @Test
    void doesNotDispenseChangeWhenAmountMatchesExactly() {
        // given
        dispenser.refill(WATER, 1);

        // when
        sut.coinInserted(Coin.FIVE);
        sut.coinInserted(Coin.FOUR);
        sut.drinkRequested(WATER);

        // then
        dispenser.assertLastDispensedDrink(WATER);
        ledger.assertNoCoinsDispensed();
    }

    @Test
    void cancelsPurchaseAndGivesMoneyBack() {
        // given
        sut.coinInserted(Coin.THREE);
        sut.coinInserted(Coin.SIX);
        ledger.fillCoins(Coin.THREE, 1);
        ledger.fillCoins(Coin.SIX, 1);

        // when
        sut.cancelRequested();

        // then
        dispenser.assertNoDrinksDispensed();
        ledger.assertCoinDispensed(Coin.THREE, 1);
        ledger.assertCoinDispensed(Coin.SIX, 1);
        display.assertLastDisplayedMessage("Dzień dobry! Zawsze Coca Cola!");
    }

    @Test
    void canMakeMultiplePurchasesInARow() {
        // given
        dispenser.refill(WATER, 1);
        dispenser.refill(COLA, 1);
        ledger.fillCoins(Coin.FOUR, 20);

        // when
        sut.coinInserted(Coin.SIX);
        sut.drinkRequested(WATER);
        sut.drinkDispensed();

        // then
        dispenser.assertLastDispensedDrink(WATER);
        ledger.assertCoinDispensed(Coin.FOUR, 2);

        // given
        ledger.clearHistory();

        // when
        sut.coinInserted(Coin.SIX);

        // then
        display.assertLastDisplayedMessage("Kwota: 5.00 PLN");

        // when
        sut.drinkRequested(COLA);

        // then
        dispenser.assertLastDispensedDrink(COLA);
        ledger.assertNoCoinsDispensed();
    }

    @Test
    void makesPurchaseAndGivesMoneyBack() {
        // given
        dispenser.refill(COLA, 1);
        sut.coinInserted(Coin.THREE);
        sut.coinInserted(Coin.SIX);
        ledger.fillCoins(Coin.THREE, 1);
        ledger.fillCoins(Coin.SIX, 1);

        // when
        sut.cancelRequested();
        sut.coinInserted(Coin.SIX);

        // then
        display.assertLastDisplayedMessage("Kwota: 5.00 PLN");

        // when
        sut.drinkRequested(COLA);

        // then
        dispenser.assertLastDispensedDrink(COLA);
    }

    @Test
    void cannotCancelWhenDrinksAreBeingDispensed() {
        // given
        dispenser.refill(COLA, 1);

        // when
        sut.coinInserted(Coin.SIX);
        ledger.fillCoins(Coin.SIX, 1);
        sut.drinkRequested(COLA);
        sut.cancelRequested();

        // then
        ledger.assertNoCoinsDispensed();
    }

    @Test
    void doesNotTakeCoinsWhenDrinkIsBeingDispensed() {
        // given
        dispenser.refill(COLA, 1);
        ledger.fillCoins(Coin.ONE, 10);

        // when
        sut.coinInserted(Coin.SIX);
        ledger.fillCoins(Coin.SIX, 1);
        sut.drinkRequested(COLA);
        sut.coinInserted(Coin.ONE);

        // then
        ledger.assertCoinDispensed(Coin.ONE, 1);
        ledger.assertCoinDispensed(Coin.SIX, 0);
    }
}