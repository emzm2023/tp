package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.add.AddDeveloperCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.developer.Developer;
import seedu.address.model.person.Person;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;
import seedu.address.testutil.DeveloperBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

public class AddDeveloperCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddDeveloperCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDeveloperAdded modelStub = new ModelStubAcceptingDeveloperAdded();
        Developer validDeveloper = new DeveloperBuilder().build();

        CommandResult commandResult = new AddDeveloperCommand(validDeveloper).execute(modelStub);

        assertEquals(String.format(AddDeveloperCommand.MESSAGE_SUCCESS, Messages.format(validDeveloper)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDeveloper), modelStub.developersAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Developer validDeveloper = new DeveloperBuilder().build();
        AddDeveloperCommand addDeveloperCommand = new AddDeveloperCommand(validDeveloper);
        ModelStub modelStub = new ModelStubWithDeveloper(validDeveloper);

        assertThrows(CommandException.class, AddDeveloperCommand.MESSAGE_DUPLICATE_DEVELOPER, () -> addDeveloperCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Developer alice = new DeveloperBuilder().withName("Alice").build();
        Developer bob = new DeveloperBuilder().withName("Bob").build();
        AddDeveloperCommand addAliceCommand = new AddDeveloperCommand(alice);
        AddDeveloperCommand addBobCommand = new AddDeveloperCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddDeveloperCommand addAliceCommandCopy = new AddDeveloperCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different developer -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddDeveloperCommand addDeveloperCommand = new AddDeveloperCommand(ALICE);
        String expected = AddDeveloperCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addDeveloperCommand.toString());
    }

    /**
     * A default model stub that have all of its methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeveloper(Developer developer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client person) {

        }

        @Override
        public void addProject(Project person) {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeveloper(Developer developer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            return false;
        }

        @Override
        public boolean hasProject(Project project) {
            return false;
        }

        @Override
        public String areProjectsValid(Person person) {
            return null;
        }

        @Override
        public void deleteDeveloper(Developer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClient(Client target) {

        }

        @Override
        public void deleteProject(Project target) {

        }

        @Override
        public void setDeveloper(Developer target, Developer editedDeveloper) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Client target, Client editedClient) {

        }

        @Override
        public void setProject(Project target, Project editedProject) {

        }

        @Override
        public ObservableList<Developer> getFilteredDeveloperList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return null;
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {
            return null;
        }

        @Override
        public void updateFilteredDeveloperList(Predicate<Developer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {

        }

        @Override
        public void updateFilteredProjectList(Predicate<Project> predicate) {

        }

        @Override
        public void updateFilteredProjectDeadlineList(Predicate<Deadline> predicate) {

        }

        @Override
        public void commitAddressBook(Model model, String message, TabIndex index) {

        }

        @Override
        public void undoAddressBook(Model model) throws CommandException {

        }

        @Override
        public void redoAddressBook(Model model) throws CommandException {

        }

        @Override
        public String getPreviousCommand() {
            return null;
        }

        @Override
        public TabIndex getPreviousTabIndex() {
            return null;
        }
    }

    /**
     * A Model stub that contains a single developer.
     */
    private class ModelStubWithDeveloper extends ModelStub {
        private final Developer developer;

        ModelStubWithDeveloper(Developer developer) {
            requireNonNull(developer);
            this.developer = developer;
        }

        @Override
        public boolean hasDeveloper(Developer developer) {
            requireNonNull(developer);
            return this.developer.isSamePerson(developer);
        }
    }

    /**
     * A Model stub that always accept the developer being added.
     */
    private class ModelStubAcceptingDeveloperAdded extends ModelStub {
        final ArrayList<Developer> developersAdded = new ArrayList<>();

        @Override
        public boolean hasDeveloper(Developer developer) {
            requireNonNull(developer);
            return developersAdded.stream().anyMatch(developer::isSamePerson);
        }

        @Override
        public void addDeveloper(Developer developer) {
            requireNonNull(developer);
            developersAdded.add(developer);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
