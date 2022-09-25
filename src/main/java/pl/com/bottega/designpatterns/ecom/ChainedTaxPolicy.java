package pl.com.bottega.designpatterns.ecom;

import java.util.LinkedList;
import java.util.List;

class ChainedTaxPolicy implements TaxPolicy {

    private final List<ChainedTaxPolicyLink> links = new LinkedList<>();

    ChainedTaxPolicy(List<ChainedTaxPolicyLink> links) {
        this.links.addAll(links);
    }

    @Override
    public Money calculate(TaxQuery query) {
        // TODO find first link that can handle query and delegate to it. If no link can be found, throw an exception.
        return null;
    }

    interface ChainedTaxPolicyLink extends TaxPolicy {
        boolean canHandle(TaxQuery query);
    }
}
