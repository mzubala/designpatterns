package pl.com.bottega.designpatterns.ecom;

import java.util.Set;

record Product(ProductId id, String name, Money price, Set<Category> categories) {
}
