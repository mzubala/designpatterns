package pl.com.bottega.designpatterns.cesar;

import java.io.IOException;
import java.io.OutputStream;

// TODO decorate output stream with decoding functionality
class CesarOutputStream extends OutputStream {

    CesarOutputStream(OutputStream decorated, int key) {

    }

    @Override
    public void write(int b) throws IOException {

    }
}
