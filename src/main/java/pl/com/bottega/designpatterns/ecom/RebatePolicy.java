package pl.com.bottega.designpatterns.ecom;

import java.util.List;

interface RebatePolicy {
    List<Rebate> calculate(Cart cart);
}

record Rebate(
    ProductId productId,
    String description,
    Money value
) {

}
