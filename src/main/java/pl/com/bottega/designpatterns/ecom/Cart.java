package pl.com.bottega.designpatterns.ecom;

import java.util.LinkedList;
import java.util.List;

class Cart {
    private final CartId id;

    private final List<CartItem> items = new LinkedList<>();

    Cart(CartId id) {
        this.id = id;
    }

    void add(Product product) {

    }

    void remove(ProductId pid) {

    }

    void updateCount(ProductId pid, Integer newCount) {

    }

    Money getTotal() {
        return null;
    }

    List<CartItem.Snapshot> getItems() {
        return null;
    }
}
