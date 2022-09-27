package pl.com.bottega.designpatterns.decoratordemo;

import java.util.List;

interface Finder {

    List<String> find(FinderQuery finderQuery);

}

record FinderQuery(String param1, String param2) {

}
