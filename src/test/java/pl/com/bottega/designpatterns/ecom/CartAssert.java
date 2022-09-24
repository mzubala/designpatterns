package pl.com.bottega.designpatterns.ecom;

import io.vavr.Tuple2;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;

class CartAssert extends ObjectAssert<Cart> {

    static CartAssert assertThat(Cart cart) {
        return new CartAssert(cart);
    }

    protected CartAssert(Cart cart) {
        super(cart);
    }

    CartAssert isEmpty() {
        Assertions.assertThat(actual.getItems()).isEmpty();
        return this;
    }

    CartAssert hasTotal(Money expectedTotal) {
        Assertions.assertThat(actual.getTotal()).isEqualTo(expectedTotal);
        return this;
    }

    CartAssert containsProduct(ProductId productId) {
        // TODO
        return this;
    }

    CartAssert doesNotContainProduct(ProductId productId) {
        // TODO
        return this;
    }

    CartAssert containsExactlyInTheSameOrder(Tuple2<Product, Integer>... productsWithCounts) {
        // TODO
        return this;
    }
}
