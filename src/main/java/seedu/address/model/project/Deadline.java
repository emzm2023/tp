package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.commons.Date;

/**
 * Represents a Project's deadline in the address book.
 * Guarantees: immutable; fields are validated.
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS =
            "Deadline should be of the format dd-MM-yyyy,<DESCRIPTION>,<HIGH|MEDIUM|LOW>,<0|1>\n" +
                    "Eg: 31-12-2019,Develop front end interface,HIGH,0";
    public static final String VALIDATION_REGEX = "^[0-3]\\d-[01]\\d-\\d{4},[^,]+,(HIGH|MEDIUM|LOW),(0|1)$";

    private final Date date;
    private final Description desc;
    private final Priority priority;

    private final boolean isDone;
    private final int num;

    /**
     * Every field must be present and not null.
     */
    public Deadline(String str, int num) {
        requireNonNull(str);
        checkArgument(isValidDeadline(str), MESSAGE_CONSTRAINTS);
        String[] output = str.split(",");
        this.date = new Date(output[0]);
        this.desc = new Description(output[1]);
        this.priority = Priority.valueOf(output[2]);
        this.isDone = output[3].contains("1");
        this.num = num;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDeadline(String text) {
        if (text == null || !text.matches(VALIDATION_REGEX))
            return false;
        return true;
    }

    public Date getDate() {
        return date;
    }

    public Description getDescription() {
        return desc;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getNum() {
        return num;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return date.equals(otherDeadline.date)
                && desc.equals(otherDeadline.desc)
                && priority.equals(otherDeadline.priority);
    }

    public String getPrintedStringRepresentation() {
        return "" + num + ". " + desc.toString() + " by: " + date.toString() + ", priority: " + priority.toString()
                + " (" + (isDone ? "done)" : "undone)");
    }

    public String getStringRepresentation() {
        return date.toString() + "," + desc.toString() + "," + priority.toString() + "," + (isDone ? "1" : "0");
    }

    /**
     * Returns a String representation of the Deadline such that it is marked as completed.
     *
     * @return A String representing the Deadline if it was completed.
     */
    public String getDoneStringRepresentation() {
        return date.toString() + "," + desc.toString() + "," + priority.toString() + "," + ("1");
    }

    /**
     * Returns a String representation of the Deadline such that it is marked as incomplete.
     *
     * @return A String representing the Deadline if it was incomplete.
     */
    public String getUndoneStringRepresentation() {
        return date.toString() + "," + desc.toString() + "," + priority.toString() + "," + ("0");
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, desc, priority);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", num)
                .add("date", date)
                .add("description", desc)
                .add("priority", priority)
                .toString();
    }

    public boolean getIsDone() {
        return isDone;
    }
}
