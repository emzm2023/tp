package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.client.Client;
import seedu.address.model.developer.Developer;
import seedu.address.model.person.Person;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Developer> PREDICATE_SHOW_ALL_DEVELOPERS = unused -> true;
    Predicate<Client> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;
    Predicate<Project> PREDICATE_SHOW_ALL_PROJECTS = unused -> true;
    Predicate<Developer> PREDICATE_SHOW_NO_DEVELOPER = unused -> false;
    Predicate<Client> PREDICATE_SHOW_NO_CLIENT = unused -> false;
    Predicate<Project> PREDICATE_SHOW_NO_PROJECT = unused -> false;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasDeveloper(Developer person);

    boolean hasClient(Client client);

    boolean hasProject(seedu.address.model.project.Project project);

    /**
     * Returns null if the projects assigned to a person exist and are valid, returns the invalid project name 
     * otherwise.
     *
     * @param person The person to check.
     * @returns The String of the invalid project name, or null if all projects are valid.
     */
    String areProjectsValid(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteDeveloper(Developer target);

    void deleteClient(Client target);

    void deleteProject(seedu.address.model.project.Project target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addDeveloper(Developer person);

    void addClient(Client person);

    void addProject(seedu.address.model.project.Project person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setDeveloper(Developer target, Developer editedDeveloper);

    void setClient(Client target, Client editedClient);

    void setProject(seedu.address.model.project.Project target, seedu.address.model.project.Project editedProject);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Developer> getFilteredDeveloperList();

    ObservableList<Client> getFilteredClientList();

    ObservableList<seedu.address.model.project.Project> getFilteredProjectList();


    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeveloperList(Predicate<Developer> predicate);

    void updateFilteredClientList(Predicate<Client> predicate);

    void updateFilteredProjectList(Predicate<seedu.address.model.project.Project> predicate);

    void updateFilteredProjectDeadlineList(Predicate<Deadline> predicate);

    void commitAddressBook(Model model, String message, TabIndex index);

    void undoAddressBook(Model model) throws CommandException;

    void redoAddressBook(Model model) throws CommandException;

    String getPreviousCommand();

    TabIndex getPreviousTabIndex();

}
