package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.developer.Developer;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
public class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_DEVELOPER = "Persons list contains duplicate developer(s).";
    public static final String MESSAGE_DUPLICATE_CLIENT = "Persons list contains duplicate client(s).";
    public static final String MESSAGE_DUPLICATE_PROJECT = "Persons list contains duplicate project(s).";

    private final List<JsonAdaptedDeveloper> developers = new ArrayList<>();
    private final List<JsonAdaptedClient> clients = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("developers") List<JsonAdaptedDeveloper> developers,
                                       @JsonProperty("clients") List<JsonAdaptedClient> clients,
                                       @JsonProperty("projects") List<JsonAdaptedProject> projects) {
        this.developers.addAll(developers);
        this.clients.addAll(clients);
        this.projects.addAll(projects);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        developers.addAll(source.getDeveloperList().stream().map(JsonAdaptedDeveloper::new).collect(Collectors.toList()));
        clients.addAll(source.getClientList().stream().map(JsonAdaptedClient::new).collect(Collectors.toList()));
        projects.addAll(source.getProjectList().stream().map(JsonAdaptedProject::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedDeveloper jsonAdaptedDeveloper : developers) {
            Developer developer = jsonAdaptedDeveloper.toModelType();
            if (addressBook.hasDeveloper(developer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DEVELOPER);
            }
            addressBook.addDeveloper(developer);
        }
        for (JsonAdaptedClient jsonAdaptedClient : clients) {
            Client client = jsonAdaptedClient.toModelType();
            if (addressBook.hasClient(client)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLIENT);
            }
            addressBook.addClient(client);
        }
        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            seedu.address.model.project.Project project = jsonAdaptedProject.toModelType();
            if (addressBook.hasProject(project)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
            }
            addressBook.addProject(project);
        }

        return addressBook;
    }

}
