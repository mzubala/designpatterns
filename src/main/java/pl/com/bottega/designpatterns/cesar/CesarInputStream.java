package pl.com.bottega.designpatterns.cesar;

import java.io.IOException;
import java.io.InputStream;

// TODO decorate input stream with encoding functionality
class CesarInputStream extends InputStream {

    CesarInputStream(InputStream decorated, CesarCoder cesarCoder) {

    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
