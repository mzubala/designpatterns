package pl.com.bottega.designpatterns.sodamachine;

import org.junit.jupiter.api.Test;

class SodaMachineControllerTest {

    private final TestLedger ledger = new TestLedger();
    private final TestDisplay display = new TestDisplay();
    private final SodaMachineController sut = new SodaMachineController(display, ledger);

    @Test
    void printsHelloMessageWhenStarted() {
        // expect
        display.assertLastDisplayedMessage("Dzień dobry! Zawsze Coca Cola!");
    }
}