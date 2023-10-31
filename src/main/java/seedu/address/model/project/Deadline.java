package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
    
    private final Date objDate;
    private final Description objDesc;
    private final Priority objPriority;

    private boolean boolIsDone;
    private final SimpleStringProperty date;
    private final SimpleStringProperty description;
    private final SimpleStringProperty priority;

    private SimpleBooleanProperty isDone;

    /**
     * Every field must be present and not null.
     */
    public Deadline(Date date, Description desc, Priority priority, boolean isDone) {
        requireAllNonNull(date, desc,priority,isDone);
        this.objDate = date;
        this.date = new SimpleStringProperty(objDate.toString());
        
        this.objDesc = desc;
        this.description = new SimpleStringProperty(objDesc.desc);
        
        this.objPriority = priority;
        this.priority = new SimpleStringProperty(objPriority.toString());
        
        this.boolIsDone = isDone;
        this.isDone = new SimpleBooleanProperty(boolIsDone);
    }
    
    public Deadline (String str) {
        requireNonNull(str);
        checkArgument(isValidDeadline(str), MESSAGE_CONSTRAINTS);
        String[] output = str.split(",");
        this.objDate = new Date(output[0]);
        this.date = new SimpleStringProperty(objDate.toString());
        
        this.objDesc = new Description(output[1]);
        this.description = new SimpleStringProperty(objDesc.desc);
        
        this.objPriority = Priority.valueOf(output[2]);
        this.priority = new SimpleStringProperty(objPriority.toString());
        
        this.boolIsDone = output[3].contains("1");
        this.isDone = new SimpleBooleanProperty(boolIsDone);
    }
    
    public void mark() {
        boolIsDone = true;
        this.isDone = new SimpleBooleanProperty(boolIsDone);
    }

    public void unmark() {
        boolIsDone = false;
        this.isDone = new SimpleBooleanProperty(boolIsDone);
    }
    
    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDeadline(String text) {
        if (text == null || !text.matches(VALIDATION_REGEX))
            return false;
        return true;
    }
    
    public Date getObjDate() {
        return objDate;
    }
    
    public Description getDescription() {
        return objDesc;
    }
    
    public Priority getObjPriority() {
        return objPriority;
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
        return objDate.equals(otherDeadline.objDate)
                && objDesc.equals(otherDeadline.objDesc)
                && objPriority.equals(otherDeadline.objPriority);
    }

    public String getStringRepresentation() {
        return objDate.toString()+","+ objDesc.toString()+","+ objPriority.toString()+","+(boolIsDone ? "1" : "0");
    }

    @Override
    public int hashCode() {
        return Objects.hash(objDate, objDesc, objPriority);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", objDate)
                .add("description", objDesc)
                .add("priority", objPriority)
                .toString();
    }

    public boolean getIsDone() {
        return boolIsDone;
    }
}
