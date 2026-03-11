package wertinator;

import org.junit.jupiter.api.Test;
import wertinator.task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for Task date handling.
 */
public class TaskTest {

    @Test
    public void setEventDates_validDates_success() {
        Task task = new Task("camp", Task.TaskTypes.EVENT);

        task.setFromDateFromString("2026-03-10");
        task.setToDateFromString("2026-03-12");

        assertEquals("[E][ ] camp (from: Mar 10 2026 to: Mar 12 2026)", task.toString());
    }

    @Test
    public void setByDateFromString_invalidDate_throwsException() {
        Task task = new Task("submit report", Task.TaskTypes.DEADLINE);

        assertThrows(IllegalArgumentException.class,
                () -> task.setByDateFromString("10-03-2026"));
    }
}
