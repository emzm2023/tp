package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo successful! The change below has been undone: ";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.undoAddressBook(model);
        String previousCommand = model.getPreviousCommand();
        TabIndex index = model.getPreviousTabIndex();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + previousCommand, index);
    }
}
