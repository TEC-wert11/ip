import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {

    public enum TaskTypes { TODO, DEADLINE, EVENT }

    private String name;
    private TaskTypes taskType;
    private boolean doneness;
    private LocalDate date;
    private String remarks = "";


    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Task(String name, TaskTypes taskType) {
        this.name = name;
        this.taskType = taskType;
        this.doneness = false;
        this.date = null;
    }

    //getters
    public String getName() {
        return name;
    }
    public TaskTypes getTaskType() {
        return taskType;
    }
    public boolean isDone() {
        return doneness;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getRemarks() {
        return remarks;
    }

    //setters
    public void setRemarks(String remarks) {
        this.remarks = (remarks == null) ? "" : remarks;
    }

    public void setDone(boolean done) {
        this.doneness = done;
    }

    public void setDateFromString(String dateStr) {
        if (dateStr == null) {
            throw new IllegalArgumentException("Null is not a date");
        }
        String trimmed = dateStr.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Theres no date here");
        }
        try {
            this.date = LocalDate.parse(trimmed); // expects yyyy-mm-dd
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("You gotta type the date in the format of yyyy-mm-dd, brotherman.");
        }
    }

    public String getFormattedDate() {
        if (date == null) return "";
        return date.format(DISPLAY_FORMAT);
    }

    public void markAsDone() {
        this.doneness = true;
    }

    public void markAsUndone() {
        this.doneness = false;
    }

    @Override
    public String toString() {
        String doneSymbol = doneness ? "X" : " ";
        String typeSymbol = switch (taskType) {
            case TODO -> "T";
            case DEADLINE -> "D";
            case EVENT -> "E";
        };

        if (taskType == TaskTypes.DEADLINE && date != null) {
            return "[" + typeSymbol + "][" + doneSymbol + "] " + name + " (by: " + getFormattedDate() + ")";
        }
        if (taskType == TaskTypes.EVENT && date != null) {
            return "[" + typeSymbol + "][" + doneSymbol + "] " + name + " (at: " + getFormattedDate() + ")";
        }

        if (!remarks.isBlank()) {
            return "[" + typeSymbol + "][" + doneSymbol + "] " + name + " (" + remarks + ")";
        }
        return "[" + typeSymbol + "][" + doneSymbol + "] " + name;
    }
}