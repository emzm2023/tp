package seedu.address.model.person.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Unable to find client in operation");
    }
}
