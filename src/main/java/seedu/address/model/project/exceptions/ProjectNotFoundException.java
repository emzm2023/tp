package seedu.address.model.project.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super("Unable to find project in operation");
    }
}
