package pl.com.bottega.designpatterns.ecom;

class NoTaxPolicy implements TaxPolicy {
    @Override
    public Money calculate(TaxQuery query) {
        return Money.zero(query.customer().preferredCurrency());
    }
}
