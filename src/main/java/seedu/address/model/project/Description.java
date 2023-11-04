package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Project's description in the address book.
 * Guarantees: immutable; is validated.
 */
public class Description {
    public static final String MESSAGE_CONSTRAINTS = "Description should only contain alphanumeric characters and "
            + "spaces, and it should not be blank.";


    public static final String VALIDATION_REGEX = "^(?!\\\\s*$)[a-zA-Z0-9 ]+$";

    public final String desc;

    /**
     * Constructs a {@code Description}.
     *
     * @param desc The String description.
     */
    public Description(String desc) {
        requireNonNull(desc);
        checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.desc = desc;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return desc;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDesc = (Description) other;
        return desc.equals(otherDesc.desc);
    }

    @Override
    public int hashCode() {
        return desc.hashCode();
    }

}
