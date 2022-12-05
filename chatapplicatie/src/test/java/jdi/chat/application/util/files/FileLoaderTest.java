package jdi.chat.application.util.files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {

    private FileLoader sut;

    @BeforeEach
    void setup() {
        sut = new FileLoader();
    }

    @Test
    void testConfigurationLoader() {
        // Arrange
        var path = "configuration/test.txt";
        var key = "test";
        var expected = "Hello world!";

        // Act
        sut.readConfigFile(path);
        var actual = sut.getValue(key);

        // Assert
        assertEquals(expected, actual);
    }
}