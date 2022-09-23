package pl.com.bottega.designpatterns.cesar;

import java.io.File;

class CesarFacade {

    private final int key;

    CesarFacade(int key) {
        this.key = key;
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
        // TODO rewrite input file to a decorated FileOutputStream
    }

    void decode(File inputFile, File outputFile) {
        // TODO rewrite input file from a decorated FileInputStream to an output file
    }
}
