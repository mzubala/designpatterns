package pl.com.bottega.designpatterns.ecom;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.com.bottega.designpatterns.ecom.CategoryBuilder.aCategory;
import static pl.com.bottega.designpatterns.ecom.CustomerBuilder.aCustomer;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.TEN_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.USD;
import static pl.com.bottega.designpatterns.ecom.PLAddressBuilder.aPLAddress;
import static pl.com.bottega.designpatterns.ecom.ProductBuilder.aProduct;
import static pl.com.bottega.designpatterns.ecom.TaxQueryBuilder.aTaxQuery;
import static pl.com.bottega.designpatterns.ecom.USAddressBuilder.aUSAddress;

class PLTaxPolicyLinkTest {

    private PLTaxPolicyLink sut = new PLTaxPolicyLink();

    @Test
    void handlesCalculationsForPLCustomers() {
        // given
        var plCustomer = aCustomer().withAddress(aPLAddress());
        var usCustomer = aCustomer().withAddress(aUSAddress());

        // expect
        assertTrue(sut.canHandle(aTaxQuery().withCustomer(plCustomer).build()));
        assertFalse(sut.canHandle(aTaxQuery().withCustomer(usCustomer).build()));
    }

    @Test
    void foodTaxIsFivePercent() {
        // given
        var product = aProduct().withCategories(Set.of(aCategory().withName("Jedzenie"))).withPrice(TEN_USD);

        // expect
        assertThat(sut.calculate(aTaxQuery().withProduct(product).withCount(2).build())).isEqualTo(new Money(ONE, USD));
    }

    @Test
    void buildingMaterialsTaxIsEightPercent() {
        // given
        var product = aProduct().withCategories(Set.of(aCategory().withName("Budowa"))).withPrice(TEN_USD);

        // expect
        assertThat(sut.calculate(aTaxQuery().withProduct(product).withCount(2).build())).isEqualTo(new Money(new BigDecimal(1.6), USD));
    }

    @Test
    void otherProductsAreTaxed23Percent() {
        var product = aProduct().withCategories(Set.of(aCategory().withName("Other"))).withPrice(TEN_USD);

        // expect
        assertThat(sut.calculate(aTaxQuery().withProduct(product).withCount(2).build())).isEqualTo(new Money(new BigDecimal(4.6), USD));
    }
}