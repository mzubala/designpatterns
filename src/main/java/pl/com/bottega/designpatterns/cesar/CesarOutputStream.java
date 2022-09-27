package pl.com.bottega.designpatterns.cesar;

import java.io.IOException;
import java.io.OutputStream;

class CesarOutputStream extends OutputStream {

    private final OutputStream decorated;
    private final CesarCoder cesarCoder;

    CesarOutputStream(OutputStream decorated, CesarCoder cesarCoder) {
        this.decorated = decorated;
        this.cesarCoder = cesarCoder;
    }

    @Override
    public void write(int b) throws IOException {
        decorated.write(cesarCoder.encode((char) b));
    }

    @Override
    public void close() throws IOException {
        decorated.close();
    }
}
