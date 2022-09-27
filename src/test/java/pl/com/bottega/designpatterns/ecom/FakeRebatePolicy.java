package pl.com.bottega.designpatterns.ecom;

import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class FakeRebatePolicy implements RebatePolicy {

    private final List<Rebate> rebates = new LinkedList<>();

    void givenRebates(Tuple2<Product, Money>... rebates) {
        this.rebates.addAll(
            Arrays.stream(rebates).map(r -> new Rebate(r._1.id(), "Rebate to " + r._1.name(), r._2)).toList()
        );
    }

    @Override
    public List<Rebate> calculate(Cart cart) {
        var items = cart.getItems();
        return rebates.stream().filter(r -> items.stream().anyMatch(i -> i.product().id().equals(r.productId()))).toList();
    }

    void givenNoRebates() {
        rebates.clear();
    }
}
