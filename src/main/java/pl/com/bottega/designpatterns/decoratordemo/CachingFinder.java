package pl.com.bottega.designpatterns.decoratordemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CachingFinder extends FinderDecorator {

    private final Map<FinderQuery, List<String>> cache = new HashMap<>();

    CachingFinder(Finder decorated) {
        super(decorated);
    }

    @Override
    public List<String> find(FinderQuery finderQuery) {
        var cachedValue = cache.get(finderQuery);
        if(cachedValue != null) {
            return cachedValue;
        }
        var result = decorated.find(finderQuery);
        cache.put(finderQuery, result);
        return result;
    }
}
