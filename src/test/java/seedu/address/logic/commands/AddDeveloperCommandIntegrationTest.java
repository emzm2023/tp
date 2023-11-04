package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.add.AddDeveloperCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Developer;
import seedu.address.testutil.PersonBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) for {@code AddDeveloperCommand}.
 */
public class AddDeveloperCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Developer validDeveloper = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validDeveloper);

        assertCommandSuccess(new AddDeveloperCommand(validDeveloper), model,
                String.format(AddDeveloperCommand.MESSAGE_SUCCESS, Messages.format(validDeveloper)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Developer developerInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddDeveloperCommand(developerInList), model,
                AddDeveloperCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
