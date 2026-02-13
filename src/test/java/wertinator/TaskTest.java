package wertinator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void setDateFromString_validIsoDate_formatsCorrectly() {
        Task t = new Task("return book", Task.TaskTypes.DEADLINE);
        t.setDateFromString("2026-02-01");
        assertEquals("Feb 01 2026", t.getFormattedDate());
        assertTrue(t.toString().contains("(by: Feb 01 2026)"));
    }

    @Test
    public void setDateFromString_invalid_throwsIllegalArgumentException() {
        Task t = new Task("return book", Task.TaskTypes.DEADLINE);
        assertThrows(IllegalArgumentException.class, () -> t.setDateFromString("01-02-2026"));
        assertThrows(IllegalArgumentException.class, () -> t.setDateFromString("   "));
        assertThrows(IllegalArgumentException.class, () -> t.setDateFromString(null));
    }
}
