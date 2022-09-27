package pl.com.bottega.designpatterns.decoratordemo;

import java.util.List;

class DemoApp {
    public static void main(String[] args) {
        var finder1 = new ListFinder(List.of("ala", "ma", "kota"));
        var finder2 = new NetworkFinder();

        var cachingNetworkFinder = new CachingFinder(finder2);
        var cachingNewtworFinderWithSlowQueryDetection = new CachingFinder(new SlowQueryDetectingFinder(finder2, 500L));
        var cachingListFinder = new CachingFinder(finder1);

        finder1.find(new FinderQuery("ala", "ma"));
        cachingNewtworFinderWithSlowQueryDetection.find(new FinderQuery("ala", "ma"));
    }
}
