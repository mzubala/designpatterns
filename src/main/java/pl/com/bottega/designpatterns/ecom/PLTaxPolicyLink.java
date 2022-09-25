package pl.com.bottega.designpatterns.ecom;

class PLTaxPolicyLink implements ChainedTaxPolicy.ChainedTaxPolicyLink {
    @Override
    public boolean canHandle(TaxQuery query) {
        // TODO check if customer address is in PL
        return false;
    }

    @Override
    public Money calculate(TaxQuery query) {
        // TODO return proper tax based on product category
        return null;
    }
}
