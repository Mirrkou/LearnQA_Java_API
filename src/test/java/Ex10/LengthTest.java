package Ex10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LengthTest {

    @Test
    public void testLengthPassed() {
        String description = "Audit and summary description";
        int length = description.length();

        assertTrue(length >= 15, "Длина < 15 символов");
    }

    @Test
    public void testLengthFailed() {
        String description = "Audit and summary description";
        int length = description.length();

        assertEquals(15, length, "Длина != 15 символам");
    }
}
