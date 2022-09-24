package pl.com.bottega.designpatterns.ecom;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.UUID;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static pl.com.bottega.designpatterns.ecom.CustomerBuilder.aCustomer;
import static pl.com.bottega.designpatterns.ecom.FakerHolder.FAKER;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.FIFTEEN_USD;

@With
@AllArgsConstructor
@NoArgsConstructor
class CartBuilder {

    CartId cartId = aCartId();
    CustomerBuilder customer = aCustomer();

    static CartId aCartId() {
        return new CartId(UUID.randomUUID());
    }
    static Cart anEmptyCart() {
        return aCart().build();
    }

    static CartBuilder aCart() {
        return new CartBuilder();
    }

    Cart build() {
        return new Cart(cartId, customer.build());
    }
}

@With
@AllArgsConstructor
@NoArgsConstructor
class ProductBuilder {
    private ProductId productId = aProductId();
    private String name = FAKER.funnyName().name();
    private Money price = FIFTEEN_USD;
    private Set<CategoryBuilder> categories = Set.of();

    ProductId aProductId() {
        return new ProductId(UUID.randomUUID());
    }

    Product build() {
        return null;
    }
}

class MoneyFixtures {
    static final Money FIVE_USD = new Money(new BigDecimal(5), Currency.getInstance("USD"));
    static final Money TEN_USD = new Money(TEN, Currency.getInstance("USD"));
    static final Money FIFTEEN_USD = new Money(new BigDecimal(15), Currency.getInstance("USD"));
    static final Money ONE_EUR = new Money(ONE, Currency.getInstance("EUR"));
}

@With
@AllArgsConstructor
@NoArgsConstructor
class CategoryBuilder {
    private CategoryId id;
    private String name = FAKER.cat().name();

    Category build() {
        return null;
    }
}

class FakerHolder {
    static final Faker FAKER = new Faker();
}

@With
@AllArgsConstructor
@NoArgsConstructor
class CustomerBuilder {
    CustomerId id = aCustomerId();
    CustomerType type = CustomerType.OCCASIONAL;
    static CustomerId aCustomerId() {
        return new CustomerId(UUID.randomUUID());
    }

    static CustomerBuilder aCustomer() {
        return new CustomerBuilder();
    }

    Customer build() {
        return new Customer(id, type);
    }
}