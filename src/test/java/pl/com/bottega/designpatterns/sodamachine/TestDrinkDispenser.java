package pl.com.bottega.designpatterns.sodamachine;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestDrinkDispenser implements DrinkDispenser {

    private final Deque<Drink> dispensedDrinks = new LinkedList<>();

    private Map<Drink, Integer> availableCounts = new HashMap<>();

    @Override
    public void dispense(Drink drink) {
        var count = getAvailableCount(drink);
        if(count == 0) {
            throw new IllegalStateException("No drink to dispense");
        }
        dispensedDrinks.add(drink);
        availableCounts.put(drink, count - 1);
    }

    @Override
    public Integer getAvailableCount(Drink drink) {
        return availableCounts.getOrDefault(drink, 0);
    }

    void refill(Drink drink, Integer count) {
        availableCounts.put(drink, count);
    }

    void assertLastDispensedDrink(Drink drink) {
        assertThat(dispensedDrinks.peekLast()).isEqualTo(drink);
    }

    void assertNoDrinksDispensed() {
        assertThat(dispensedDrinks.size()).isEqualTo(0);
    }
}
