package pl.com.bottega.designpatterns.ecom;

import java.util.Set;

record Product(ProductId id, String name, Money price, Set<Category> categories) {
    boolean hasCategory(String name) {
        return categories.stream().anyMatch(cat -> cat.name().equals(name));
    }
}
