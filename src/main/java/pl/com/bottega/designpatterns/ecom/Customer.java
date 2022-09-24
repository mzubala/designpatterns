package pl.com.bottega.designpatterns.ecom;

import java.util.Currency;

record Customer(CustomerId id, CustomerType customerType, Currency preferredCurrency) {
}

enum CustomerType {
    NEW, OCCASIONAL, FREQUENT, CHAMPION
}
