package pl.com.bottega.designpatterns.ecom;

import io.vavr.control.Option;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class PromotionsEngine implements RebatePolicy {

    private final Promotion firstPromotion;

    PromotionsEngine(Promotion firstPromotion) {
        this.firstPromotion = firstPromotion;
    }

    @Override
    public List<Rebate> calculate(Cart cart) {
        List<Rebate> rebates = new LinkedList<>();
        firstPromotion.calculate(cart, rebates);
        return rebates;
    }
}

abstract class Promotion {
    protected Option<Promotion> next = Option.none();

    abstract void calculate(Cart cart, List<Rebate> accumulator);

    void setNext(Promotion next) {
        this.next = Option.of(next);
    }

    Promotion link(Promotion other) {
        setNext(other);
        return other;
    }

    protected void proceed(Cart cart, List<Rebate> accumulator) {
        next.peek(promotion -> promotion.calculate(cart, accumulator));
    }
}

class BeautyPromotion extends Promotion {

    @Override
    void calculate(Cart cart, List<Rebate> accumulator) {
        var beautyProducts = cart.getItems().stream().map(item -> item.product()).filter(item -> item.hasCategory("Uroda"));
        var rebates = beautyProducts.map(product -> new Rebate(product.id(), "-10% dla urody", product.price().times(new BigDecimal(0.1))));
        accumulator.addAll(rebates.toList());
        proceed(cart, accumulator);
    }
}

class XMasPromotion extends Promotion {

    private final Supplier<Clock> clockSupplier;
    XMasPromotion(Supplier<Clock> clockSupplier) {
        this.clockSupplier = clockSupplier;
    }

    @Override
    void calculate(Cart cart, List<Rebate> accumulator) {
        if (xmasTime()) {
            rebateRate(cart).peek(rate -> {
                var rebates = cart.getItems().stream().map(item -> rebate(rate, item));
                accumulator.addAll(rebates.toList());
            });
        }
        proceed(cart, accumulator);
    }

    private static Rebate rebate(BigDecimal rate, CartItem.Snapshot item) {
        return new Rebate(item.product().id(), "XMas Promo", item.product().price().times(rate));
    }

    private boolean xmasTime() {
        var now = clockSupplier.get().instant().atZone(ZoneId.systemDefault());
        var month = now.get(ChronoField.MONTH_OF_YEAR);
        var day = now.get(ChronoField.DAY_OF_MONTH);
        return month == 12 && day >= 20 && day <= 24;
    }

    private Option<BigDecimal> rebateRate(Cart cart) {
        if (cart.getCustomer().customerType() == CustomerType.CHAMPION) {
            return Option.of(new BigDecimal("0.05"));
        } else if (cart.getCustomer().customerType() == CustomerType.FREQUENT) {
            return Option.of(new BigDecimal("0.03"));
        } else {
            return Option.none();
        }
    }
}

class FoodPromotion extends Promotion {

    @Override
    void calculate(Cart cart, List<Rebate> accumulator) {
        var foodItems = cart.getItems().stream().filter(item -> item.product().hasCategory("Jedzenie"));
        var itemsCount = foodItems.collect(Collectors.summarizingInt(CartItem.Snapshot::count));
        if (itemsCount.getSum() >= 5) {
            var sortedProducts = foodItems.map(CartItem.Snapshot::product)
                .sorted(Comparator.comparing(Product::price));
            var cheapestProduct = sortedProducts.findFirst();
            cheapestProduct.ifPresent(product -> accumulator.add(new Rebate(product.id(), "Jedzenie gratis!", product.price())));
        }
    }
}

