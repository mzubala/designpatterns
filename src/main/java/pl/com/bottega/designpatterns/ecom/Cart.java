package pl.com.bottega.designpatterns.ecom;

import java.util.LinkedList;
import java.util.List;

class Cart {
    private final CartId id;

    private final Customer customer;

    private final List<CartItem> items = new LinkedList<>();

    Cart(CartId id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    void add(Product product) {
        // TODO
    }

    void remove(ProductId pid) {
        // TODO
    }

    void updateCount(ProductId pid, Integer newCount) {
        // TODO
    }

    Money getTotal() {
        // TODO
        return null;
    }

    List<CartItem.Snapshot> getItems() {
        // TODO
        return null;
    }
}
