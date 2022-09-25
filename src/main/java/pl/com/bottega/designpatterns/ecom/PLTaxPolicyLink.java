package pl.com.bottega.designpatterns.ecom;

import java.math.BigDecimal;

class PLTaxPolicyLink implements ChainedTaxPolicy.ChainedTaxPolicyLink {

    private static final String FOOD_CATEGORY = "Jedzenie";
    private static final String BUILDING_CATEGORY = "Budowa";
    private static final FixedRateTaxPolicy FIVE_PERCENT = new FixedRateTaxPolicy(new BigDecimal(0.05));
    private static final FixedRateTaxPolicy EIGHT_PERCENT = new FixedRateTaxPolicy(new BigDecimal(0.08));
    private static final FixedRateTaxPolicy TWENTY_THREE_PERCENT = new FixedRateTaxPolicy(new BigDecimal(0.23));

    @Override
    public boolean canHandle(TaxQuery query) {
        return query.customer().address() instanceof PLAddress;
    }

    @Override
    public Money calculate(TaxQuery query) {
        if(query.product().hasCategory(FOOD_CATEGORY)) {
            return FIVE_PERCENT.calculate(query);
        } else if(query.product().hasCategory(BUILDING_CATEGORY)) {
            return EIGHT_PERCENT.calculate(query);
        }
        return TWENTY_THREE_PERCENT.calculate(query);
    }
}
