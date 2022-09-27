package pl.com.bottega.designpatterns.ecom;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.com.bottega.designpatterns.ecom.CustomerBuilder.aCustomer;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.TEN_USD;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.USD;
import static pl.com.bottega.designpatterns.ecom.PLAddressBuilder.aPLAddress;
import static pl.com.bottega.designpatterns.ecom.ProductBuilder.aProduct;
import static pl.com.bottega.designpatterns.ecom.TaxQueryBuilder.aTaxQuery;
import static pl.com.bottega.designpatterns.ecom.USAddressBuilder.aUSAddress;

class USTaxPolicyLinkTest {
    private USTaxPolicyLink sut = new USTaxPolicyLink();

    @Test
    void handlesCalculationsForUSCustomers() {
        // given
        var plCustomer = aCustomer().withAddress(aPLAddress());
        var usCustomer = aCustomer().withAddress(aUSAddress());

        // expect
        assertTrue(sut.canHandle(aTaxQuery().withCustomer(usCustomer).build()));
        assertFalse(sut.canHandle(aTaxQuery().withCustomer(plCustomer).build()));
    }

    @Test
    void deTaxIsFourPercent() {
        // given
        var customer = aCustomer().withAddress(aUSAddress().withState(USState.DELAWARE));

        // expect
        assertThat(sut.calculate(
            aTaxQuery().withProduct(aProduct()).withCustomer(customer).withNetAmount(TEN_USD.times(2)).build())
        ).isEqualTo(new Money(new BigDecimal(0.8), USD));
    }

    @Test
    void vaTaxIsFivePercent() {
        // given
        var customer = aCustomer().withAddress(aUSAddress().withState(USState.VIRGINIA));

        // expect
        assertThat(sut.calculate(
            aTaxQuery().withProduct(aProduct()).withCustomer(customer).withNetAmount(TEN_USD.times(2)).build())
        ).isEqualTo(new Money(ONE, USD));
    }

    @Test
    void inOtherStatesTaxIsSevenPercent() {
        var customer = aCustomer().withAddress(aUSAddress().withState(USState.CALIFORNIA));

        // expect
        assertThat(sut.calculate(
            aTaxQuery().withProduct(aProduct().withPrice(TEN_USD)).withCustomer(customer).withNetAmount(TEN_USD.times(2)).build())
        ).isEqualTo(new Money(new BigDecimal(1.40), USD));
    }

}