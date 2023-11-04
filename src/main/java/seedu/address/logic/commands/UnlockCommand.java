package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DEVELOPERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.Password;

public class UnlockCommand extends Command {
    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_SUCCESS = "Unlocked all data";
    public static final String MESSAGE_FAILURE = "Incorrect Password!\n";
    public static final Object MESSAGE_USAGE =
            COMMAND_WORD + ": Please unlock using correct format\n" + "Example: " + COMMAND_WORD + " pw/Password123!";
    private String input;

    public UnlockCommand(String input) {
        this.input = input;
    }

    @Override
    public CommandResult execute(Model model) {
        assert model != null : "Model cannot be null";
        requireNonNull(model);
        if (Password.verifyPassword(input)) {
            model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
            model.updateFilteredDeveloperList(PREDICATE_SHOW_ALL_DEVELOPERS);
            model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
            AddressBookParser.unlock();
            return new CommandResult(MESSAGE_SUCCESS, TabIndex.Developer);
        }
        return new CommandResult(String.format(MESSAGE_FAILURE, Password.MESSAGE_CONSTRAINTS), TabIndex.Developer);
    }
}
