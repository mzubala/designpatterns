package pl.com.bottega.designpatterns.ecom;

class USTaxPolicyLink implements ChainedTaxPolicy.ChainedTaxPolicyLink {
    @Override
    public boolean canHandle(TaxQuery query) {
        // TODO check if the customer is within the US
        return false;
    }

    @Override
    public Money calculate(TaxQuery query) {
        // TODO calculate using a rate based on the customer's state
        return null;
    }
}
