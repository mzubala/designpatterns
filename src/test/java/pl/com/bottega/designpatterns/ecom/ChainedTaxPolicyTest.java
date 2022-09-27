package pl.com.bottega.designpatterns.ecom;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.com.bottega.designpatterns.ecom.CustomerBuilder.aCustomer;
import static pl.com.bottega.designpatterns.ecom.MoneyFixtures.TEN_USD;
import static pl.com.bottega.designpatterns.ecom.ProductBuilder.aProduct;
import static pl.com.bottega.designpatterns.ecom.TaxQueryBuilder.aTaxQuery;

class ChainedTaxPolicyTest {

    private final ChainedTaxPolicy.ChainedTaxPolicyLink l1 = mock(ChainedTaxPolicy.ChainedTaxPolicyLink.class);
    private final ChainedTaxPolicy.ChainedTaxPolicyLink l2 = mock(ChainedTaxPolicy.ChainedTaxPolicyLink.class);
    private final ChainedTaxPolicy.ChainedTaxPolicyLink l3 = mock(ChainedTaxPolicy.ChainedTaxPolicyLink.class);
    private final ChainedTaxPolicy sut = new ChainedTaxPolicy(List.of(l1, l2, l3));

    @Test
    void calculatesTaxUsingFirstLinkSupportingTheQuery() {
        // given
        var query = aTaxQuery().build();
        when(l2.canHandle(query)).thenReturn(true);
        when(l2.calculate(query)).thenReturn(TEN_USD);

        // when
        var tax = sut.calculate(query);

        // then
        assertThat(tax).isEqualTo(TEN_USD);
    }

    @Test
    void throwsExceptionIfNoLinkSupportsTheQuery() {
        // given
        var query = aTaxQuery().build();

        // expect
        assertThatThrownBy(() -> {
            sut.calculate(query);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
