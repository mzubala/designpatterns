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
        return links.stream().filter(link -> link.canHandle(query)).findFirst()
            .map(link -> link.calculate(query))
            .orElseThrow(() -> unsupportedQuery(query));
    }

    private static IllegalArgumentException unsupportedQuery(TaxQuery query) {
        return new IllegalArgumentException(String.format("Do know how to calculate tax for %s", query.toString()));
    }
    interface ChainedTaxPolicyLink extends TaxPolicy {
        boolean canHandle(TaxQuery query);
    }
}
