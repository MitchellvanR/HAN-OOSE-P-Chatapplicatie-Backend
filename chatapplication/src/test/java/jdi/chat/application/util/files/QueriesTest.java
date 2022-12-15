package jdi.chat.application.util.files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QueriesTest {
    private Queries sut;

    @BeforeEach
    void setup() {
        sut = Queries.getInstance();
    }

    @Test
    void testQueryLoader() {
        // Arrange
        var key = "test";
        var expected = "Hello query!";

        // Act
        var actual = sut.getQuery(key);

        // Assert
        assertEquals(expected, actual);
    }

}