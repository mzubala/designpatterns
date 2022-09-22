package pl.com.bottega.desigpatterns.marsrover;

import org.assertj.core.api.AbstractStringAssert;

import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputAssertions {

    private final PipedOutputStream pipedOutputStream = new PipedOutputStream();

    private final Scanner scanner;

    OutputAssertions() {
        try {
            var pipedInputStream = new PipedInputStream(pipedOutputStream);
            scanner = new Scanner(pipedInputStream);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    AbstractStringAssert<?> assertThatNextLine() {
        return assertThat(scanner.nextLine());
    }

    OutputStream toOutputStream() {
        return pipedOutputStream;
    }
}
