package pl.com.bottega.designpatterns.decoratordemo;

import lombok.extern.java.Log;

import java.util.List;

@Log
class SlowQueryDetectingFinder extends FinderDecorator {
    private final Long slowQueryThreshold;

    SlowQueryDetectingFinder(Finder decorated, Long slowQueryThreshold) {
        super(decorated);
        this.slowQueryThreshold = slowQueryThreshold;
    }

    @Override
    public List<String> find(FinderQuery finderQuery) {
        var ts = System.currentTimeMillis();
        var result = decorated.find(finderQuery);
        var te = System.currentTimeMillis();
        if(te - ts > slowQueryThreshold) {
            log.info(String.format("Slow query detected: %s, took: %d", finderQuery, te - ts));
        }
        return result;
    }
}
