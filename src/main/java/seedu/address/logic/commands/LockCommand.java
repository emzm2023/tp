package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_CLIENT;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_DEVELOPER;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_PROJECT;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;

public class LockCommand extends Command {
    public static final String COMMAND_WORD = "lock";

    public static final String MESSAGE_SUCCESS = "Locked all data";


    @Override
    public CommandResult execute(Model model) {
        assert model != null : "Model cannot be null";
        requireNonNull(model);
        model.updateFilteredClientList(PREDICATE_SHOW_NO_CLIENT);
        model.updateFilteredDeveloperList(PREDICATE_SHOW_NO_DEVELOPER);
        model.updateFilteredProjectList(PREDICATE_SHOW_NO_PROJECT);
        AddressBookParser.lock();
        return new CommandResult(MESSAGE_SUCCESS, TabIndex.Developer);
    }
}
