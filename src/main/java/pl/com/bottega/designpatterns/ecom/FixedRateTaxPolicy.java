package pl.com.bottega.designpatterns.ecom;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;

class FixedRateTaxPolicy implements TaxPolicy {

    private final BigDecimal rate;

    FixedRateTaxPolicy(BigDecimal rate) {
        this.rate = rate;
        checkArgument(rate.compareTo(BigDecimal.ZERO) >= 0 && rate.compareTo(BigDecimal.ONE) <= 0);
    }

    @Override
    public Money calculate(TaxQuery query) {
        return query.netAmount().times(rate);
    }
}
