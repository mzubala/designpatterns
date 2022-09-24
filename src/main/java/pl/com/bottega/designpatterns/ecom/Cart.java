package pl.com.bottega.designpatterns.ecom;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkArgument;

class Cart {
    private final CartId id;

    private final Customer customer;
    private final TaxPolicy taxPolicy;

    private final List<CartItem> items = new LinkedList<>();

    Cart(CartId id, Customer customer, TaxPolicy taxPolicy) {
        this.id = id;
        this.customer = customer;
        this.taxPolicy = taxPolicy;
    }

    void add(Product product) {
        checkArgument(product.price().currency().equals(customer.preferredCurrency()));
        items.add(new CartItem(this, product, 1));
    }

    void remove(ProductId pid) {
        items.removeIf(itemWithId(pid));
    }

    void updateCount(ProductId pid, Integer newCount) {
        checkArgument(newCount > 0);
        var item = items.stream().filter(itemWithId(pid)).findFirst().orElseThrow(() -> {
            throw new IllegalArgumentException("No such item");
        });
        item.updateCount(newCount);
    }

    Money getTotal() {
        return items.stream().reduce(Money.zero(customer.preferredCurrency()), (total, item) -> total.add(item.getTotal()), Money::add);
    }

    List<CartItem.Snapshot> getItems() {
        return items.stream().map(CartItem::getSnapshot).toList();
    }

    Customer getCustomer() {
        return customer;
    }

    TaxPolicy getTaxPolicy() {
        return taxPolicy;
    }

    private static Predicate<CartItem> itemWithId(ProductId pid) {
        return cartItem -> cartItem.getProductId().equals(pid);
    }
}
