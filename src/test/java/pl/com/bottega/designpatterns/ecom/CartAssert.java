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

    CartAssert hasTotal(Money expectedTotal) {
        return this;
    }

    CartAssert containsProduct(ProductId productId) {
        return this;
    }

    CartAssert doesNotContainProduct(ProductId productId) {
        return this;
    }

    CartAssert containsExactlyInTheSameOrder(Tuple2<ProductId, Integer>... productsWithCounts) {
        return null;
    }

    CartAssert isEmpty() {
        Assertions.assertThat(actual.getItems()).isEmpty();
        return this;
    }
}
