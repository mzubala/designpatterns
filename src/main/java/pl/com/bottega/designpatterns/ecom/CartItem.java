package pl.com.bottega.designpatterns.ecom;

class CartItem {
    private Product product;
    private Integer count;

    CartItem(Product product, Integer count) {
        this.product = product;
        this.count = count;
        // TODO add cart
    }

    void updateCount(Integer newCount) {
        this.count = newCount;
    }

    Money getTotal() {
        // add tax
        return product.price().times(count);
    }

    Snapshot getSnapshot() {
        return new Snapshot(product, count, getTotal());
    }

    ProductId getProductId() {
        return product.id();
    }

    record Snapshot(
        Product product, Integer count, Money total
    ) {
    }
}
