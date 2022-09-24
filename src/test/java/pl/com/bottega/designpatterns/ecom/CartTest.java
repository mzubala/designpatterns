package pl.com.bottega.designpatterns.ecom;

import org.junit.jupiter.api.Test;

import static pl.com.bottega.designpatterns.ecom.CartAssert.assertThat;
import static pl.com.bottega.designpatterns.ecom.CartBuilder.anEmptyCart;

class CartTest {

    @Test
    void aNewCartIsEmpty() {
        // given
        var cart = anEmptyCart();

        // expect
        assertThat(cart).isEmpty();
    }

    @Test
    void anEmptyCartHasZeroTotal() {

    }

    @Test
    void addsItems() {

    }

    @Test
    void removesItems() {

    }

    @Test
    void updatesItemsCount() {

    }

    @Test
    void cartTotalChangesWhenItemsAreAdded() {

    }

    @Test
    void cartTotalChangesWhenItemsAreRemoved() {

    }

    @Test
    void cartTotalChangesWhenItemsCountIsUpdated() {

    }
}
