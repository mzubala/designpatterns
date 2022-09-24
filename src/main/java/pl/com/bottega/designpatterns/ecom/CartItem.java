package pl.com.bottega.designpatterns.ecom;

class CartItem {
    private Product product;
    private Integer count;

    CartItem(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }

    void updateCount(Integer newCount) {
        // TODO
    }

    Money getTotal() {
        // TODO
        return null;
    }

    Snapshot getSnapshot() {
        // TODO
        return null;
    }

    ProductId getProductId() {
        // TODO
        return null;
    }

    record Snapshot(
        Product product, Integer count, Money total
    ) {
    }
}
