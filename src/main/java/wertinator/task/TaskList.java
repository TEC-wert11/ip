package wertinator.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from loaded tasks.
     *
     * @param loadedTasks tasks loaded from storage
     */
    public TaskList(List<Task> loadedTasks) {
        assert loadedTasks != null : "Loaded task list should not be null";

        tasks = new ArrayList<>();
        for (int i = 0; i < loadedTasks.size(); i++) {
            assert loadedTasks.get(i) != null : "Loaded task should not be null";
            tasks.add(loadedTasks.get(i));
        }
    }

    /**
     * Adds a task into the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        assert task != null : "Task to add should not be null";
        tasks.add(task);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index
     * @return task at the given index
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Task index out of bounds";
        return tasks.get(index);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index zero-based index
     * @return removed task
     */
    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "Task index out of bounds";
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks.
     *
     * @return number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns all tasks.
     *
     * @return list of all tasks
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }

    /**
     * Finds tasks whose names contain the given keyword.
     *
     * @param keyword keyword to search
     * @return matching tasks
     */
    public TaskList findMatching(String keyword) {
        assert keyword != null : "Keyword should not be null";

        TaskList matches = new TaskList();
        String k = keyword.trim().toLowerCase();

        for (Task t : tasks) {
            assert t != null : "Task in list should not be null";
            if (t.getName().toLowerCase().contains(k)) {
                matches.add(t);
            }
        }
        return matches;
    }
}