package pl.com.bottega.designpatterns.ecom;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

class Cart {
    private final CartId id;

    private final Customer customer;
    private final TaxPolicy taxPolicy;
    private final RebatePolicy rebatePolicy;

    private final List<CartItem> items = new LinkedList<>();

    Cart(CartId id, Customer customer, TaxPolicy taxPolicy, RebatePolicy rebatePolicy) {
        this.id = id;
        this.customer = customer;
        this.taxPolicy = taxPolicy;
        this.rebatePolicy = rebatePolicy;
    }

    void add(Product product) {
        checkArgument(product.price().currency().equals(customer.preferredCurrency()));
        items.add(new CartItem(this, product, 1));
        calculateRebates();
    }

    void remove(ProductId pid) {
        items.removeIf(itemWithId(pid));
        calculateRebates();
    }

    void updateCount(ProductId pid, Integer newCount) {
        checkArgument(newCount > 0);
        CartItem item = getCartItem(pid);
        item.updateCount(newCount);
        calculateRebates();
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

    private void calculateRebates() {
        var rebatesMap = rebatePolicy.calculate(this)
            .stream().collect(Collectors.groupingBy(Rebate::productId));
        items.forEach(i -> i.updateRebates(rebatesMap.getOrDefault(i.getProductId(), List.of())));
    }

    private CartItem getCartItem(ProductId pid) {
        var item = items.stream().filter(itemWithId(pid)).findFirst().orElseThrow(() -> {
            throw new IllegalArgumentException("No such item");
        });
        return item;
    }
}
