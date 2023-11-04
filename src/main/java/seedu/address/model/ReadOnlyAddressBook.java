package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.developer.Developer;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Developer> getDeveloperList();

    ObservableList<Client> getClientList();

    ObservableList<seedu.address.model.project.Project> getProjectList();


}
