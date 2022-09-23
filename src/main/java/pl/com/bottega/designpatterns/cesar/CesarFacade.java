package pl.com.bottega.designpatterns.cesar;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class CesarFacade {

    private final CesarCoder coder;

    CesarFacade(int key) {
        coder = new CesarCoder(key);
    }

    @SneakyThrows
    String encode(String text) {
        var bos = new ByteArrayOutputStream();
        try (var os = new CesarOutputStream(bos, coder)) {
            os.write(text.getBytes());
            return bos.toString();
        }

    }

    @SneakyThrows
    String decode(String encodedText) {
        var bis = new ByteArrayInputStream(encodedText.getBytes());
        try (var is = new CesarInputStream(bis, coder)) {
            return new String(is.readAllBytes());
        }
    }

    @SneakyThrows
    void encode(File inputFile, File outputFile) {
        try (var in = new FileInputStream(inputFile); var out = new CesarOutputStream(new FileOutputStream(outputFile), coder)) {
            IOUtils.copy(in, out);
        }
    }

    @SneakyThrows
    void decode(File inputFile, File outputFile) {
        try (var in = new CesarInputStream(new FileInputStream(inputFile), coder); var out = new FileOutputStream(outputFile)) {
            IOUtils.copy(in, out);
        }
    }
}
