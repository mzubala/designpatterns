package pl.com.bottega.designpatterns.marsrover;

import lombok.SneakyThrows;
import org.assertj.core.api.AbstractStringAssert;

import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputAssertions {

    private final PipedOutputStream pipedOutputStream = new PipedOutputStream();

    private final Scanner scanner;

    @SneakyThrows
    OutputAssertions() {
        var pipedInputStream = new PipedInputStream(pipedOutputStream);
        scanner = new Scanner(pipedInputStream);
    }

    AbstractStringAssert<?> assertThatNextLine() {
        return assertThat(scanner.nextLine());
    }

    void skipLine() {
        scanner.nextLine();
    }

    OutputStream toOutputStream() {
        return pipedOutputStream;
    }

    @SneakyThrows
    void close() {
        pipedOutputStream.close();
        scanner.close();
    }
}
