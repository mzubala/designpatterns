package pl.com.bottega.designpatterns.cesar;

import java.io.IOException;
import java.io.InputStream;

class CesarInputStream extends InputStream {

    private final InputStream decorated;
    private final CesarCoder coder;

    CesarInputStream(InputStream decorated, CesarCoder coder) {
        this.decorated = decorated;
        this.coder = coder;
    }

    @Override
    public int read() throws IOException {
        var b = decorated.read();
        if(b == -1) {
            return -1;
        }
        return coder.decode((char) b);
    }
}
