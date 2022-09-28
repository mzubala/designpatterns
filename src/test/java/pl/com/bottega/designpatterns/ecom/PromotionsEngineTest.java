package pl.com.bottega.designpatterns.ecom;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;
import java.util.function.Supplier;

import static pl.com.bottega.designpatterns.ecom.CartAssert.assertThat;
import static pl.com.bottega.designpatterns.ecom.CartBuilder.aCart;
import static pl.com.bottega.designpatterns.ecom.CategoryBuilder.aCategory;
import static pl.com.bottega.designpatterns.ecom.CustomerBuilder.aCustomer;
import static pl.com.bottega.designpatterns.ecom.CustomerType.CHAMPION;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.FIVE_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.TEN_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.USD;
import static pl.com.bottega.designpatterns.ecom.ProductBuilder.aProduct;

class PromotionsEngineTest {

    @Test
    void appliesXMasRebate() {
        // given
        Supplier<Clock> clockSupplier = () -> Clock.fixed(Instant.parse("2022-12-21T15:00:00Z"), ZoneId.systemDefault());
        var firstPromo = new BeautyPromotion();
        firstPromo.link(new XMasPromotion(clockSupplier)).link(new FoodPromotion());
        var engine = new PromotionsEngine(firstPromo);
        var cart = aCart().withRebatePolicy(engine).withCustomer(aCustomer().withType(CHAMPION)).build();
        var p1 = aProduct().withCategories(Set.of(aCategory().withName("Other"))).withPrice(TEN_USD).build();

        // when
        cart.add(p1);

        // then
        assertThat(cart).hasTotal(new Money(new BigDecimal("9.50"), USD));
    }

    @Test
    void appliesXmasAndBeautyRebates() {
        // given
        Supplier<Clock> clockSupplier = () -> Clock.fixed(Instant.parse("2022-12-21T15:00:00Z"), ZoneId.systemDefault());
        var firstPromo = new BeautyPromotion();
        firstPromo.link(new XMasPromotion(clockSupplier)).link(new FoodPromotion());
        var engine = new PromotionsEngine(firstPromo);
        var cart = aCart().withRebatePolicy(engine).withCustomer(aCustomer().withType(CHAMPION)).build();
        var p1 = aProduct().withCategories(Set.of(aCategory().withName("Uroda"))).withPrice(TEN_USD).build();

        // when
        cart.add(p1);

        // then
        assertThat(cart).hasTotal(new Money(new BigDecimal("8.50"), USD));
    }

    @Test
    void appliesAllRebates() {
        // given
        Supplier<Clock> clockSupplier = () -> Clock.fixed(Instant.parse("2022-12-21T15:00:00Z"), ZoneId.systemDefault());
        var firstPromo = new BeautyPromotion();
        firstPromo.link(new XMasPromotion(clockSupplier)).link(new FoodPromotion());
        var engine = new PromotionsEngine(firstPromo);
        var cart = aCart().withRebatePolicy(engine).withCustomer(aCustomer().withType(CHAMPION)).build();
        var p1 = aProduct().withCategories(Set.of(aCategory().withName("Uroda"))).withPrice(TEN_USD).build();
        var p2 = aProduct().withCategories(Set.of(aCategory().withName("Jedzenie"))).withPrice(FIVE_USD).build();

        // when
        cart.add(p1);
        cart.add(p2);
        cart.updateCount(p2.id(), 5);

        // then
        assertThat(cart).hasTotal(new Money(new BigDecimal("27.25"), USD));
    }

}
