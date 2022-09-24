package pl.com.bottega.designpatterns.ecom;

import io.vavr.Tuple2;
import org.assertj.core.api.AbstractAssert;

class CartAssert extends AbstractAssert<CartAssert, Cart> {

    static CartAssert assertThat(Cart cart) {
        return new CartAssert(cart);
    }

    protected CartAssert(Cart cart) {
        super(cart, CartAssert.class);
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
}
