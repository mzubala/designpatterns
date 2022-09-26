package pl.com.bottega.designpatterns.sodamachine;

class SodaMachineController {

    private final Display display;
    private final Ledger ledger;

    SodaMachineController(Display display, Ledger ledger) {
        this.display = display;
        this.ledger = ledger;
    }

    void coinInserted(Coin coin) {

    }

    void cancelRequested() {

    }

    void drinkRequested(Drink drink) {

    }
}
