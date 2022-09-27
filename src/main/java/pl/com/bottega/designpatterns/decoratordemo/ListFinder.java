package pl.com.bottega.designpatterns.decoratordemo;

import java.util.List;

class ListFinder implements Finder {

    private final List<String> items;

    ListFinder(List<String> items) {
        this.items = items;
    }

    @Override
    public List<String> find(FinderQuery finderQuery) {
        return items.stream().filter(item -> item.equals(finderQuery.param1())).toList();
    }
}
