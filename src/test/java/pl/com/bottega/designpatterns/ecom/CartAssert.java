package pl.com.bottega.designpatterns.ecom;

import io.vavr.Tuple2;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;

import java.util.function.Predicate;

class CartAssert extends ObjectAssert<Cart> {

    static CartAssert assertThat(Cart cart) {
        return new CartAssert(cart);
    }

    protected CartAssert(Cart cart) {
        super(cart);
    }

    CartAssert hasTotal(Money expectedTotal) {
        Assertions.assertThat(actual.getTotal()).isEqualTo(expectedTotal);
        return this;
    }

    CartAssert containsProduct(ProductId productId) {
        Assertions.assertThat(actual.getItems()).anyMatch(itemWith(productId));
        return this;
    }

    CartAssert doesNotContainProduct(ProductId productId) {
        Assertions.assertThat(actual.getItems()).noneMatch(itemWith(productId));
        return this;
    }

    CartAssert containsExactlyInTheSameOrder(Tuple2<Product, Integer>... productsWithCounts) {
        Assertions.assertThat(actual.getItems().stream().map(CartAssert::toProductWithCount)).containsExactly(productsWithCounts);
        return this;
    }

    CartAssert isEmpty() {
        Assertions.assertThat(actual.getItems()).isEmpty();
        return this;
    }

    private static Predicate<CartItem.Snapshot> itemWith(ProductId productId) {
        return (snapshot) -> snapshot.product().id().equals(productId);
    }

    private static Tuple2<Product, Integer> toProductWithCount(CartItem.Snapshot snapshot) {
        return new Tuple2<>(snapshot.product(), snapshot.count());
    }
}
