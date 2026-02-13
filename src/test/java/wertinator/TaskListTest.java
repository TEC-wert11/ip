package wertinator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void add_increasesSize_andGetReturnsSameTask() {
        TaskList list = new TaskList();
        Task t = new Task("read book", Task.TaskTypes.TODO);

        int before = list.size();
        list.add(t);

        assertEquals(before + 1, list.size());
        assertSame(t, list.get(before));
    }

    @Test
    public void remove_returnsRemovedTask_andShiftsElements() {
        TaskList list = new TaskList();
        Task t1 = new Task("t1", Task.TaskTypes.TODO);
        Task t2 = new Task("t2", Task.TaskTypes.TODO);
        Task t3 = new Task("t3", Task.TaskTypes.TODO);

        list.add(t1);
        list.add(t2);
        list.add(t3);

        Task removed = list.remove(1);
        assertSame(t2, removed);
        assertEquals(2, list.size());
        assertSame(t3, list.get(1)); // shifted left
    }

    @Test
    public void get_onEmptyList_throwsIndexOutOfBounds() {
        TaskList list = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }
}
