package pl.com.bottega.designpatterns.ecom;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class USTaxPolicyLink implements ChainedTaxPolicy.ChainedTaxPolicyLink {

    static final Map<USState, TaxPolicy> TAX_MAP = new HashMap<>();

    static {
        TAX_MAP.put(USState.VIRGINIA, new FixedRateTaxPolicy(new BigDecimal(0.05)));
        TAX_MAP.put(USState.DELAWARE, new FixedRateTaxPolicy(new BigDecimal(0.04)));
    }

    static final TaxPolicy OTHER_STATES_POLICY = new FixedRateTaxPolicy(new BigDecimal(0.07));

    @Override
    public boolean canHandle(TaxQuery query) {
        return query.customer().address() instanceof USAddress;
    }

    @Override
    public Money calculate(TaxQuery query) {
        var policy = TAX_MAP.getOrDefault(getState(query), OTHER_STATES_POLICY);
        return policy.calculate(query);
    }

    private USState getState(TaxQuery query) {
        return ((USAddress) query.customer().address()).state();
    }
}
