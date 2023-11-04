package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Developers (Developers are considered duplicates if they have 
 * the same
 * identity).
 */
public class DuplicateDeveloperException extends RuntimeException {
    public DuplicateDeveloperException() {
        super("Operation would result in duplicate developers");
    }
}
