package pl.com.bottega.designpatterns.ecom;

import io.vavr.control.Option;

import java.util.List;

class PromotionsEngine implements RebatePolicy {

    private final Promotion firstPromotion;

    PromotionsEngine(Promotion firstPromotion) {
        this.firstPromotion = firstPromotion;
    }

    @Override
    public List<Rebate> calculate(Cart cart) {
        // TODO implement calculate method
        return null;
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
        // TODO do work and call proceed(...)
    }
}

class XMasPromotion extends Promotion {

    @Override
    void calculate(Cart cart, List<Rebate> accumulator) {
        // TODO do work and call proceed
    }
}

class FoodPromotion extends Promotion {

    @Override
    void calculate(Cart cart, List<Rebate> accumulator) {
        // TODO do work and call proceed
    }
}

