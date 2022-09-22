package pl.com.bottega.desigpatterns.marsrover;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class FakeInput {

    private final PipedOutputStream pos = new PipedOutputStream();

    private final PipedInputStream pipedInputStream;

    FakeInput() {
        try {
            pipedInputStream = new PipedInputStream(pos);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void input(String line) {
        try {
            pos.write((line + "\n").getBytes());
            pos.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    InputStream toInputStream() {
        return pipedInputStream;
    }
}
