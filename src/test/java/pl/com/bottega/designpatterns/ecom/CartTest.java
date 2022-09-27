package pl.com.bottega.designpatterns.ecom;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static pl.com.bottega.designpatterns.ecom.CartAssert.assertThat;
import static pl.com.bottega.designpatterns.ecom.CartBuilder.anEmptyCart;
import static pl.com.bottega.designpatterns.ecom.CategoryBuilder.aCategory;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.TEN_USD;
import static pl.com.bottega.designpatterns.ecom.ProductBuilder.aProduct;

class CartTest {

    @Test
    void aNewCartIsEmpty() {
        // TODO
    }

    @Test
    void anEmptyCartHasZeroTotal() {
        // TODO
    }

    @Test
    void addsItems() {
        // TODO
    }

    @Test
    void removesItems() {
        // TODO
    }

    @Test
    void updatesItemsCount() {
        // TODO
    }

    @Test
    void cartTotalChangesWhenItemsAreAdded() {
        //given
        var cart = anEmptyCart();
        var product = aProduct().withPrice(TEN_USD)
            .withCategories(Set.of(aCategory().withName("Budownictwo"),
                aCategory()))
            .build();

        // when
        cart.add(product);

        // then
        assertThat(cart).hasTotal(TEN_USD);
    }

    @Test
    void cartTotalChangesWhenItemsAreRemoved() {
        // TODO
    }

    @Test
    void cartTotalChangesWhenItemsCountIsUpdated() {
        // TODO
    }

    @Test
    void itemsCountCannotBeNegative() {
        // TODO
    }

    @Test
    void cannotChangeCountOfANonExistingItem() {
        // TODO
    }

    @Test
    void cannotAddProductsWithPriceInADifferentCurrency() {
        // TODO
    }
}
