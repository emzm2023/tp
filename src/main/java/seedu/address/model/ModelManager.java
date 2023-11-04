package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.client.Client;
import seedu.address.model.developer.Developer;
import seedu.address.model.person.Person;
import seedu.address.model.project.Deadline;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Developer> filteredDevelopers;
    private final FilteredList<Client> filteredClients;
    private final FilteredList<seedu.address.model.project.Project> filteredProjects;
    private final VersionedAddressBook versionedAddressBook;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredDevelopers = new FilteredList<>(this.addressBook.getDeveloperList());
        filteredClients = new FilteredList<>(this.addressBook.getClientList());
        filteredProjects = new FilteredList<>(this.addressBook.getProjectList());
        versionedAddressBook = new VersionedAddressBook(this.addressBook);

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasDeveloper(Developer developer) {
        requireNonNull(developer);
        return addressBook.hasDeveloper(developer);
    }

    @Override
    public void deleteDeveloper(Developer target) {
        addressBook.removeDeveloper(target);
    }

    @Override
    public void addDeveloper(Developer developer) {
        addressBook.addDeveloper(developer);
        updateFilteredDeveloperList(PREDICATE_SHOW_ALL_DEVELOPERS);
    }

    @Override
    public void setDeveloper(Developer target, Developer editedDeveloper) {
        requireAllNonNull(target, editedDeveloper);

        addressBook.setDeveloper(target, editedDeveloper);
    }

    // Similarly, create methods for Client and Project

    //=========== Filtered Developer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Developer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Developer> getFilteredDeveloperList() {
        return filteredDevelopers;
    }

    @Override
    public void updateFilteredDeveloperList(Predicate<Developer> predicate) {
        requireNonNull(predicate);
        filteredDevelopers.setPredicate(predicate);
    }

    @Override
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return addressBook.hasClient(client);
    }


    public String areProjectsValid(Person person) {
        requireNonNull(person);
        return addressBook.areProjectsValid(person);
    }

    @Override
    public void deleteClient(Client target) {
        addressBook.removeClient(target);
    }

    @Override
    public void addClient(Client client) {
        addressBook.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
    }

    @Override
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);
        addressBook.setClient(target, editedClient);
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return filteredClients;
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    @Override
    public boolean hasProject(seedu.address.model.project.Project project) {
        requireNonNull(project);
        return addressBook.hasProject(project);
    }

    @Override
    public void deleteProject(seedu.address.model.project.Project target) {
        addressBook.removeProject(target);
    }

    @Override
    public void addProject(seedu.address.model.project.Project project) {
        addressBook.addProject(project);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
    }

    @Override
    public void setProject(seedu.address.model.project.Project target, seedu.address.model.project.Project editedProject) {
        requireAllNonNull(target, editedProject);

        addressBook.setProject(target, editedProject);
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Project} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<seedu.address.model.project.Project> getFilteredProjectList() {
        return filteredProjects;
    }

    @Override
    public void updateFilteredProjectList(Predicate<seedu.address.model.project.Project> predicate) {
        requireNonNull(predicate);
        filteredProjects.setPredicate(predicate);
        filteredProjects.forEach(e -> e.setPredicate(u -> true));
    }

    @Override
    public void updateFilteredProjectDeadlineList(Predicate<Deadline> predicate) {
        requireNonNull(predicate);
        filteredProjects.forEach(e -> e.setPredicate(predicate));
    }

    //=========== Undo/Redo Accessors =============================================================
    @Override
    public void commitAddressBook(Model model, String message, TabIndex index) {
        versionedAddressBook.commit(model, message, index);
    }

    @Override
    public void undoAddressBook(Model model) throws CommandException {
        versionedAddressBook.undo(model);
    }

    @Override
    public void redoAddressBook(Model model) throws CommandException {
        versionedAddressBook.redo(model);
    }

    @Override
    public String getPreviousCommand() {
        return versionedAddressBook.getPreviousMessage();
    }

    @Override
    public TabIndex getPreviousTabIndex() {
        return versionedAddressBook.getPreviousTabIndex();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredDevelopers.equals(otherModelManager.filteredDevelopers)
                && filteredClients.equals(otherModelManager.filteredClients)
                && filteredProjects.equals(otherModelManager.filteredProjects)
                && versionedAddressBook.equals(otherModelManager.versionedAddressBook);
    }

}
