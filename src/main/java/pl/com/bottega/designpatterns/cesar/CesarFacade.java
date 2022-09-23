package pl.com.bottega.designpatterns.cesar;

import java.io.File;

class CesarFacade {

    private final CesarCoder coder;

    CesarFacade(int key) {
        coder = new CesarCoder(key);
    }

    String encode(String text) {
        // TODO create and decorate ByteArrayOutput stream, then use it to encode and get the encoded string
        return null;
    }

    String decode(String encodedText) {
        // TODO create and decorate ByteArrayInput stream, then use it to decode and get the decoded string
        return null;
    }

    void encode(File inputFile, File outputFile) {
        // TODO rewrite input file to a decorated FileOutputStream, use IOUtils.copy
    }

    void decode(File inputFile, File outputFile) {
        // TODO rewrite input file from a decorated FileInputStream to an output file, use IOUtils.copy
    }
}
