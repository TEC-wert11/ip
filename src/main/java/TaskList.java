import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<Task>();
    }

    public TaskList(List<Task> loadedTasks) {
        tasks = new ArrayList<Task>();
        for (int i = 0; i < loadedTasks.size(); i++) {
            tasks.add(loadedTasks.get(i));
        }
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }
}