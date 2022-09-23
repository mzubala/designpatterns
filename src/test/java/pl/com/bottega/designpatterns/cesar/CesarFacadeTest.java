package pl.com.bottega.designpatterns.cesar;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CesarFacadeTest {

    private final Faker faker = new Faker();

    @ParameterizedTest
    @MethodSource("testCases")
    public void encodesStrings(TestCase testCase) {
        // given
        var sut = new CesarFacade(testCase.key);

        // when
        String result = sut.encode(testCase.raw);

        // then
        assertThat(result).isEqualTo(testCase.encoded);
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void decodesStrings(TestCase testCase) {
        // given
        var sut = new CesarFacade(testCase.key);

        // when
        String result = sut.decode(testCase.encoded);

        // then
        assertThat(result).isEqualTo(testCase.raw);
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void encodesFiles(TestCase testCase) throws IOException {
        // given
        var inputFile = Files.createTempFile("inputFile", ".txt").toFile();
        var outputFile = Files.createTempFile("outputFile", ".txt").toFile();
        writeFile(inputFile, testCase.raw);
        var sut = new CesarFacade(testCase.key);

        // when
        sut.encode(inputFile, outputFile);

        // then
        assertThat(readFile(outputFile)).isEqualTo(testCase.encoded);
        Files.delete(inputFile.toPath());
        Files.delete(outputFile.toPath());
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void decodesFiles(TestCase testCase) throws IOException {
        var inputFile = Files.createTempFile("inputFile", ".txt").toFile();
        var outputFile = Files.createTempFile("outputFile", ".txt").toFile();
        writeFile(inputFile, testCase.encoded);
        var sut = new CesarFacade(testCase.key);

        // when
        sut.encode(inputFile, outputFile);

        // then
        assertThat(readFile(outputFile)).isEqualTo(testCase.raw);
        Files.delete(inputFile.toPath());
        Files.delete(outputFile.toPath());
    }

    @Test
    public void encodesAndDecodesRandomStringsWithRandomKeys() {
        // given
        var key = randomKey();
        var sut = new CesarFacade(key);
        var text = randomText();

        // when
        var encoded = sut.encode(text);
        var decoded = sut.decode(encoded);

        // then
        assertThat(decoded).withFailMessage("Failed for key [%d] and text [%s]", key, text).isEqualTo(text);
    }

    private String randomText() {
        return faker.book().title();
    }

    private int randomKey() {
        return faker.number().numberBetween(0, 0xff);
    }

    static Stream<TestCase> testCases() {
        return Stream.of(
            new TestCase("ala", "bmb", 1),
            new TestCase("750", "972", 2),
            new TestCase(
                "If he had anything confidential to say, he wrote it in cipher, that is, by so changing the order of the letters of the alphabet, that not a word could be made out.",
                "Pm ol ohk hufaopun jvumpkluaphs av zhf, ol dyval pa pu jpwoly, aoha pz, if zv johunpun aol vykly vm aol slaalyz vm aol hswohila, aoha uva h dvyk jvbsk il thkl vba.",
                7)
        );
    }

    private void writeFile(File inputFile, String raw) throws IOException {
        try (var fw = new FileWriter(inputFile)) {
            fw.write(raw);
        }
    }

    private String readFile(File outputFile) throws IOException {
        try (var fr = new BufferedReader(new FileReader(outputFile))) {
            return fr.readLine();
        }
    }

    record TestCase(
        String raw,
        String encoded,
        int key
    ) {
    }
}
