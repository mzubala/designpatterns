package pl.com.bottega.designpatterns.sodamachine;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestLedger implements Ledger {

    private final Map<Coin, Integer> coinsCounts = new HashMap<>();

    private final Map<Coin, Integer> dispensedCoins = new HashMap<>();

    @Override
    public void dispense(Coin coin) {
        var count = getCoinsCount(coin);
        if(count == 0) {
            throw new IllegalStateException("No such coin to dispense");
        }
        coinsCounts.put(coin, count - 1);
        var dispensedCount = dispensedCoins.getOrDefault(coin, 0);
        dispensedCoins.put(coin, dispensedCount + 1);
    }

    @Override
    public Integer getCoinsCount(Coin coin) {
        return coinsCounts.getOrDefault(coin, 0);
    }

    void fillCoins(Coin four, Integer count) {
        coinsCounts.put(four, count);
    }

    void assertCoinDispensed(Coin coin, int count) {
        assertThat(dispensedCoins.getOrDefault(coin, 0)).isEqualTo(count);
    }

    void assertNoCoinsDispensed() {
        assertThat(dispensedCoins.size()).isEqualTo(0);
    }

    void clearHistory() {
        dispensedCoins.clear();
    }
}
