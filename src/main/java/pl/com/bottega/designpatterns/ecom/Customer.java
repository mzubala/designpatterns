package pl.com.bottega.designpatterns.ecom;

record Customer(CustomerId id, CustomerType customerType) {
}

enum CustomerType {
    NEW, OCCASIONAL, FREQUENT, CHAMPION
}
