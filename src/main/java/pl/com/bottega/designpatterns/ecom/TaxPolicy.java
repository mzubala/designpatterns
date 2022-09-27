package pl.com.bottega.designpatterns.ecom;

interface TaxPolicy {
    Money calculate(TaxQuery query);

    record TaxQuery(Product product, Customer customer, Money netAmount) {

    }
}
