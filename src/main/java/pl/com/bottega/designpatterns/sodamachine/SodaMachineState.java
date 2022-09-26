package pl.com.bottega.designpatterns.sodamachine;

abstract sealed class SodaMachineState {

    protected final SodaMachineController controller;

    SodaMachineState(SodaMachineController controller) {
        this.controller = controller;
    }

    abstract void coinInserted(Coin coin);

    // TODO add other abstract methods here
}

final class ReadySodaMachine extends SodaMachineState {

    ReadySodaMachine(SodaMachineController controller) {
        super(controller);
    }

    @Override
    void coinInserted(Coin coin) {
        // TODO
    }
}

final class MachineWithCoinInserted extends SodaMachineState {

    MachineWithCoinInserted(SodaMachineController controller, Coin firstCoin) {
        super(controller);
    }

    @Override
    void coinInserted(Coin coin) {
        // TODO
    }
}

final class DispensingMachine extends SodaMachineState {

    DispensingMachine(SodaMachineController controller) {
        super(controller);
    }

    @Override
    void coinInserted(Coin coin) {

    }
}