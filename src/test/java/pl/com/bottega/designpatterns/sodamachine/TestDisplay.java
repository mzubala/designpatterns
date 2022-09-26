package pl.com.bottega.designpatterns.sodamachine;

import java.util.Deque;
import java.util.LinkedList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TestDisplay implements Display {

    private final Deque<String> messages = new LinkedList<>();

    private final Deque<String> briefMessages = new LinkedList<>();

    @Override
    public void show(String message) {
        messages.add(message);
    }

    @Override
    public void showBriefly(String message) {
        briefMessages.add(message);
    }

    void assertLastDisplayedMessage(String message) {
        assertThat(messages.peekLast()).isEqualTo(message);
    }

    void assertLastBrieflyDisplayedMessage(String message) {
        assertThat(briefMessages.peekLast()).isEqualTo(message);
    }
}
