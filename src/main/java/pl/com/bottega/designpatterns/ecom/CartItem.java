package pl.com.bottega.designpatterns.ecom;

class CartItem {
    private Product product;
    private Integer count;

    CartItem(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }

    void updateCount(Integer newCount) {

    }

    Money getTotal() {
        return null;
    }

    Snapshot getSnapshot() {
        return null;
    }

    record Snapshot(
        Product product, Integer count, Money total
    ) {
    }
}
