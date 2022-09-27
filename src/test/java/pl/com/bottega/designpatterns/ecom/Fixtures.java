package pl.com.bottega.designpatterns.ecom;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ONE;
import static pl.com.bottega.designpatterns.ecom.CategoryBuilder.aCategory;
import static pl.com.bottega.designpatterns.ecom.CustomerBuilder.aCustomer;
import static pl.com.bottega.designpatterns.ecom.FakerHolder.FAKER;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.FIFTEEN_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.USD;

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
    private Set<CategoryBuilder> categories = Set.of(aCategory());

    static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    static ProductId aProductId() {
        return new ProductId(UUID.randomUUID());
    }

    Product build() {
        // TODO
        return new Product(productId, name, price,
            categories.stream().map(CategoryBuilder::build)
                .collect(Collectors.toSet())
        );
    }
}

class MoneyFixtures {
    static final Currency USD = Currency.getInstance("USD");
    static final Currency EUR = Currency.getInstance("EUR");
    static final Money FIVE_USD = new Money(new BigDecimal(5), USD);
    static final Money TEN_USD = new Money(BigDecimal.TEN, USD);
    static final Money FIFTEEN_USD = new Money(new BigDecimal(15), USD);
    static final Money FIVE = new Money(new BigDecimal(5), USD);
    static final Money TEN = new Money(BigDecimal.TEN);
    static final Money FIFTEEN = new Money(new BigDecimal(15));
    static final Money ONE_EUR = new Money(ONE, EUR);
}

@With
@AllArgsConstructor
@NoArgsConstructor
class CategoryBuilder {
    private CategoryId id;
    private String name = FAKER.cat().name();

    static CategoryBuilder aCategory() {
        return new CategoryBuilder();
    }

    Category build() {
        // TODO
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

    Currency preferredCurrency = USD;

    static CustomerId aCustomerId() {
        return new CustomerId(UUID.randomUUID());
    }

    static CustomerBuilder aCustomer() {
        return new CustomerBuilder();
    }

    Customer build() {
        return new Customer(id, type, preferredCurrency);
    }
}