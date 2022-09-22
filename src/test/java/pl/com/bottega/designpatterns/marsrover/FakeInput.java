package pl.com.bottega.designpatterns.marsrover;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class FakeInput {

    private final PipedOutputStream pos = new PipedOutputStream();

    private final PipedInputStream pipedInputStream;

    @SneakyThrows
    FakeInput() {
            pipedInputStream = new PipedInputStream(pos);
    }

    @SneakyThrows
    void input(String line) {
            pos.write((line + "\n").getBytes());
            pos.flush();
        }

    @SneakyThrows
    void close() {
        pipedInputStream.close();
        pos.close();
    }

    InputStream toInputStream() {
        return pipedInputStream;
    }
}
