package pl.com.bottega.designpatterns.decoratordemo;

import lombok.SneakyThrows;

import java.util.List;

class NetworkFinder implements Finder {
    @Override
    @SneakyThrows
    public List<String> find(FinderQuery finderQuery) {
        Thread.sleep(2000);
        return List.of("result1", "result2", "result3");
    }
}
