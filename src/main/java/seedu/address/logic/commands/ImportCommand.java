package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.developer.Developer;

public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports employees from csv file.\n"
            + "Column titles should follow this format strictly:\n"
            + "Name, Contact Number, Email, Address, Date Joined, Role, Salary, Username, Password, Projects";
    public static final String MESSAGE_SUCCESS = "New developer added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = " already exists in the address book\n";

    private final ArrayList<Developer> toAddList;

    /**
     * Creates an AddDeveloperCommand to add the specified {@code Developer}
     */
    public ImportCommand(ArrayList<Developer> developerList) {
        requireNonNull(developerList);
        for (Developer i : developerList) {
            requireNonNull(i);
        }
        toAddList = developerList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String output = "";
        for (Developer toAdd : toAddList) {
            if (model.hasDeveloper(toAdd)) {
                output += toAdd.getName().fullName + MESSAGE_DUPLICATE_PERSON;
            }

            model.addDeveloper(toAdd);
            output += String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
            output += "\n";
        }
        return new CommandResult(output, TabIndex.Developer);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return toAddList.equals(otherImportCommand.toAddList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddList", toAddList)
                .toString();
    }
}
