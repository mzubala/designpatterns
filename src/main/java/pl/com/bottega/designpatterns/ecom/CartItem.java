package pl.com.bottega.designpatterns.ecom;

import java.util.LinkedList;
import java.util.List;

class CartItem {
    private final Cart cart;
    private Product product;
    private Integer count;

    private final List<Rebate> rebates = new LinkedList<>();

    CartItem(Cart cart, Product product, Integer count) {
        this.cart = cart;
        this.product = product;
        this.count = count;
    }

    void updateCount(Integer newCount) {
        this.count = newCount;
    }

    Money getSubtotal() {
        return product.price().times(count);
    }

    Money getTotal() {
        var subtotal = getSubtotal();
        var net = subtotal.sub(getRebatesTotal());
        return net.add(getTax(net));
    }

    Snapshot getSnapshot() {
        return new Snapshot(product, count, getTotal());
    }

    ProductId getProductId() {
        return product.id();
    }

    void updateRebates(List<Rebate> rebates) {
        this.rebates.clear();
        this.rebates.addAll(rebates);
    }

    private Money getTax(Money net) {
        return cart.getTaxPolicy().calculate(new TaxPolicy.TaxQuery(product, cart.getCustomer(), net));
    }

    private Money getRebatesTotal() {
        return rebates.stream().reduce(
            Money.zero(cart.getCustomer().preferredCurrency()),
            (acc, rebate) -> acc.add(rebate.value()),
            Money::add
        );
    }

    record Snapshot(
        Product product, Integer count, Money total
    ) {
    }
}
