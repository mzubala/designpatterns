package pl.com.bottega.designpatterns.decoratordemo;

import java.util.List;

abstract class FinderDecorator implements Finder {

    protected final Finder decorated;

    FinderDecorator(Finder decorated) {
        this.decorated = decorated;
    }

    @Override
    public abstract List<String> find(FinderQuery finderQuery);
}
