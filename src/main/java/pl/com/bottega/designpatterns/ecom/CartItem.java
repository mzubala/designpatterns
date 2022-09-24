package pl.com.bottega.designpatterns.ecom;

class CartItem {
    private final Cart cart;
    private Product product;
    private Integer count;

    CartItem(Cart cart, Product product, Integer count) {
        this.cart = cart;
        this.product = product;
        this.count = count;
    }

    void updateCount(Integer newCount) {
        this.count = newCount;
    }

    Money getTotal() {
        return product.price().times(count).add(getTax());
    }

    Snapshot getSnapshot() {
        return new Snapshot(product, count, getTotal());
    }

    ProductId getProductId() {
        return product.id();
    }

    private Money getTax() {
        return cart.getTaxPolicy().calculate(new TaxPolicy.TaxQuery(product, count, cart.getCustomer()));
    }

    record Snapshot(
        Product product, Integer count, Money total
    ) {
    }
}
