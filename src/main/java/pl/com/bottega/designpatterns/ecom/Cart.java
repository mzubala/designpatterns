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

    }

    void remove(ProductId pid) {

    }

    void updateCount(ProductId pid, Integer newCount) {

    }

    Money getTotal() {
        return null;
    }

    List<CartItem.Snapshot> getItems() {
        return items.stream().map(CartItem::getSnapshot).toList();
    }
}
