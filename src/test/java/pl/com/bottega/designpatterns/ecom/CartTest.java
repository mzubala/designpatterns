package pl.com.bottega.designpatterns.ecom;

import io.vavr.Tuple;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static pl.com.bottega.designpatterns.ecom.CartAssert.assertThat;
import static pl.com.bottega.designpatterns.ecom.CartBuilder.aCart;
import static pl.com.bottega.designpatterns.ecom.CartBuilder.anEmptyCart;
import static pl.com.bottega.designpatterns.ecom.CustomerBuilder.aCustomer;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.EUR;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.FIFTEEN_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.FIVE_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.TEN_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.USD;
import static pl.com.bottega.designpatterns.ecom.ProductBuilder.aProduct;

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
        // given
        var cart = aCart().withCustomer(aCustomer()
            .withPreferredCurrency(EUR)).build();

        // expect
        assertThat(cart).hasTotal(Money.zero(EUR));
    }

    @Test
    void addsItems() {
        // given
        var cart = anEmptyCart();
        var p1 = aProduct().build();
        var p2 = aProduct().build();

        // when
        cart.add(p1);
        cart.add(p2);

        // then
        assertThat(cart).containsExactlyInTheSameOrder(Tuple.of(p1, 1), Tuple.of(p2, 1));
    }

    @Test
    void removesItems() {
        // given
        var cart = anEmptyCart();
        var p1 = aProduct().build();
        var p2 = aProduct().build();
        cart.add(p1);
        cart.add(p2);

        // when
        cart.remove(p2.id());

        // then
        assertThat(cart).containsExactlyInTheSameOrder(Tuple.of(p1, 1));
    }

    @Test
    void updatesItemsCount() {
        // given
        var cart = anEmptyCart();
        var p1 = aProduct().build();
        var p2 = aProduct().build();
        cart.add(p1);
        cart.add(p2);

        // when
        cart.updateCount(p2.id(), 4);
        cart.updateCount(p1.id(), 2);

        // then
        assertThat(cart).containsExactlyInTheSameOrder(Tuple.of(p1, 2), Tuple.of(p2, 4));
    }

    @Test
    void cartTotalChangesWhenItemsAreAdded() {
        // given
        var cart = anEmptyCart();
        var p1 = aProduct().withPrice(FIVE_USD).build();
        var p2 = aProduct().withPrice(TEN_USD).build();

        // when
        cart.add(p1);
        cart.add(p2);

        // then
        assertThat(cart).hasTotal(FIFTEEN_USD);
    }

    @Test
    void cartTotalChangesWhenItemsAreRemoved() {
        var cart = anEmptyCart();
        var p1 = aProduct().withPrice(FIVE_USD).build();
        var p2 = aProduct().withPrice(TEN_USD).build();
        cart.add(p1);
        cart.add(p2);

        // when
        cart.remove(p1.id());

        // then
        assertThat(cart).hasTotal(TEN_USD);
    }

    @Test
    void cartTotalChangesWhenItemsCountIsUpdated() {
        // given
        var cart = anEmptyCart();
        var p1 = aProduct().withPrice(FIVE_USD).build();
        var p2 = aProduct().withPrice(TEN_USD).build();
        cart.add(p1);
        cart.add(p2);

        // when
        cart.updateCount(p1.id(), 2);
        cart.updateCount(p2.id(), 5);

        // then
        assertThat(cart).hasTotal(new Money(new BigDecimal(60), USD));
    }

    @Test
    void itemsCountCannotBeNegative() {
        // given
        var cart = anEmptyCart();
        var product = aProduct().build();
        cart.add(product);

        // expect
        assertThatThrownBy(() -> cart.updateCount(product.id(), 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> cart.updateCount(product.id(), -1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cannotChangeCountOfANonExistingItem() {
        // given
        var cart = anEmptyCart();

        // expect
        assertThatThrownBy(() -> cart.updateCount(ProductBuilder.aProductId(), 10)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cannotAddProductsWithPriceInADifferentCurrency() {
        // given
        var cart = aCart().withCustomer(aCustomer().withPreferredCurrency(EUR)).build();
        var product = aProduct().withPrice(TEN_USD).build();

        // expect
        assertThatThrownBy(() -> cart.add(product)).isInstanceOfAny(IllegalArgumentException.class);
    }

    @Test
    void addsTaxToTotal() {
        // given
        var p1 = aProduct().withPrice(FIVE_USD).build();
        var p2 = aProduct().withPrice(TEN_USD).build();
        var cart = aCart().withTaxPolicy(new FixedRateTaxPolicy(new BigDecimal(0.1))).build();
        cart.add(p1);
        cart.add(p2);
        cart.updateCount(p1.id(), 4);
        cart.updateCount(p2.id(), 2);

        // when
        var total = cart.getTotal();

        // then
        assertThat(cart).hasTotal(new Money(new BigDecimal(44), USD));
    }

    @Test
    void evaluatesRebatesAfterAddingItemsToCart() {
        // given
        var p1 = aProduct().withPrice(FIVE_USD).build();
        var p2 = aProduct().withPrice(FIFTEEN_USD).build();
        var rebatePolicy = new FakeRebatePolicy();
        rebatePolicy.givenRebates(Tuple.of(p2, FIVE_USD), Tuple.of(p2, FIVE_USD));
        var cart = aCart().withRebatePolicy(rebatePolicy).build();

        // when
        cart.add(p1);
        cart.add(p2);

        // then
        assertThat(cart).hasTotal(TEN_USD);

        // given
        var p3 = aProduct().withPrice(FIFTEEN_USD).build();
        rebatePolicy.givenRebates(Tuple.of(p3, FIVE_USD));

        // when
        cart.add(p3);

        // then
        assertThat(cart).hasTotal(new Money(new BigDecimal(20), USD));
    }

    @Test
    void evaluatesRebatesAfterRemovingAndUpdatingItemQuantity() {
        // given
        var p1 = aProduct().withPrice(FIVE_USD).build();
        var p2 = aProduct().withPrice(FIFTEEN_USD).build();
        var rebatePolicy = new FakeRebatePolicy();
        rebatePolicy.givenRebates(Tuple.of(p2, FIVE_USD), Tuple.of(p2, FIVE_USD));
        var cart = aCart().withRebatePolicy(rebatePolicy).build();

        // when
        cart.add(p1);
        cart.add(p2);

        // then
        assertThat(cart).hasTotal(TEN_USD);

        // given
        rebatePolicy.givenNoRebates();

        // when
        cart.remove(p1.id());

        // then
        assertThat(cart).hasTotal(FIFTEEN_USD);

        // given
        rebatePolicy.givenRebates(Tuple.of(p2, FIVE_USD));

        // when
        cart.updateCount(p2.id(), 5);

        // then
        assertThat(cart).hasTotal(new Money(new BigDecimal(70), USD));
    }
}
