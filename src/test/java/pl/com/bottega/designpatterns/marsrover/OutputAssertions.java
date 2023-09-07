package pl.com.bottega.designpatterns.marsrover;

import lombok.SneakyThrows;
import org.assertj.core.api.AbstractStringAssert;
import org.awaitility.Awaitility;

import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.time.Duration;
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
        Awaitility.await().atMost(Duration.ofMillis(100)).failFast(() -> {
            scanner.nextLine();
        });
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
