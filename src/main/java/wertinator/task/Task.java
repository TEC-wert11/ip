package wertinator.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * task class
 */
public class Task {

    public enum TaskTypes { TODO, DEADLINE, EVENT }

    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final String name;
    private final TaskTypes taskType;
    private boolean doneness;
    private LocalDate byDate;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String remarks = "";

    /**
     * Creates a task with a name and type.
     *
     * @param name task name
     * @param taskType type of task
     */
    public Task(String name, TaskTypes taskType) {
        this.name = name;
        this.taskType = taskType;
        this.doneness = false;
        this.byDate = null;
        this.fromDate = null;
        this.toDate = null;
    }

    /**
     * Returns the task name.
     *
     * @return task name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the task type.
     *
     * @return task type
     */
    public TaskTypes getTaskType() {
        return taskType;
    }

    /**
     * Returns whether the task is done.
     *
     * @return true if done
     */
    public boolean isDone() {
        return doneness;
    }

    /**
     * Returns the deadline date.
     *
     * @return deadline date
     */
    public LocalDate getByDate() {
        return byDate;
    }

    /**
     * Returns the event start date.
     *
     * @return event start date
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * Returns the event end date.
     *
     * @return event end date
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * Returns remarks.
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets remarks for the task.
     *
     * @param remarks remarks to set
     */
    public void setRemarks(String remarks) {
        if (remarks == null) {
            this.remarks = "";
        } else {
            this.remarks = remarks;
        }
    }

    /**
     * Sets whether the task is done.
     *
     * @param done done status
     */
    public void setDone(boolean done) {
        this.doneness = done;
    }

    /**
     * Sets the deadline date from a string in yyyy-mm-dd format.
     *
     * @param dateStr deadline date string
     */
    public void setByDateFromString(String dateStr) {
        this.byDate = parseDate(dateStr);
    }

    /**
     * Sets the event start date from a string in yyyy-mm-dd format.
     *
     * @param dateStr event start date string
     */
    public void setFromDateFromString(String dateStr) {
        this.fromDate = parseDate(dateStr);
    }

    /**
     * Sets the event end date from a string in yyyy-mm-dd format.
     *
     * @param dateStr event end date string
     */
    public void setToDateFromString(String dateStr) {
        this.toDate = parseDate(dateStr);
    }

    /**
     * Returns remarks.
     *
     * @param date date to format
     * @return formatted date string
     */
    private String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DISPLAY_FORMAT);
    }

    /**
     * Parses a date string in yyyy-mm-dd format.
     *
     * @param dateStr date string
     * @return parsed LocalDate
     */
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null) {
            throw new IllegalArgumentException("Null is not a date");
        }

        String trimmed = dateStr.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Theres no date here");
        }

        try {
            return LocalDate.parse(trimmed);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "You gotta type the date in the format of yyyy-mm-dd, brotherman.");
        }
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.doneness = true;
    }

    /**
     * Marks the task as undone.
     */
    public void markAsUndone() {
        this.doneness = false;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return formatted task string
     */
    @Override
    public String toString() {
        String doneSymbol;
        if (doneness) {
            doneSymbol = "X";
        } else {
            doneSymbol = " ";
        }

        String typeSymbol;
        switch (taskType) {
            case TODO:
                typeSymbol = "T";
                break;
            case DEADLINE:
                typeSymbol = "D";
                break;
            case EVENT:
                typeSymbol = "E";
                break;
            default:
                typeSymbol = "?";
        }

        String base = "[" + typeSymbol + "][" + doneSymbol + "] " + name;

        if (taskType == TaskTypes.DEADLINE && byDate != null) {
            return base + " (by: " + formatDate(byDate) + ")";
        }

        if (taskType == TaskTypes.EVENT && fromDate != null && toDate != null) {
            return base + " (from: " + formatDate(fromDate)
                    + " to: " + formatDate(toDate) + ")";
        }

        if (!remarks.isBlank()) {
            return base + " (" + remarks + ")";
        }

        return base;
    }
}