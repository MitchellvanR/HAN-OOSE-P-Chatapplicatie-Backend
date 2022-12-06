package jdi.chat.application.util.files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {
    private Config sut;

    @BeforeEach
    void setup() {
        sut = Config.getInstance();
    }

    @Test
    void testConfigurationLoader() {
        // Arrange
        var key = "test";
        var expected = "Hello config!";

        // Act
        var actual = sut.getConfiguration(key);

        // Assert
        assertEquals(expected, actual);
    }
}