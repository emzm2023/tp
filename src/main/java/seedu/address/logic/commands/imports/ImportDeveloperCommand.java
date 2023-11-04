package seedu.address.logic.commands.imports;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.developer.Developer;

public class ImportDeveloperCommand extends Command {
    public static final String COMMAND_WORD = "import-developer";
    //Name name, Phone phone, Email email, Address address, Role role, Set<String> projects,
    //                     Salary salary, Date dateJoined, GithubId githubId, Rating rating

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports developers from csv file.\n"
            + "Column titles should follow this format strictly:\n"
            + "Name, Contact Number, Email, Address, Date Joined, Role, Salary, GithubId, Rating, Projects";
    public static final String MESSAGE_SUCCESS = "New developer added: %1$s";
    public static final String MESSAGE_DUPLICATE_DEVELOPER = " is a developer that already exists in the address " +
            "book\n";

    private final ArrayList<Developer> toAddList;

    /**
     * Creates an AddDeveloperCommand to add the specified {@code Developer}
     */
    public ImportDeveloperCommand(ArrayList<Developer> developerList) {
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
                output += toAdd.getName().fullName + MESSAGE_DUPLICATE_DEVELOPER;
            } else {
                model.addDeveloper(toAdd);
                output += String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
            }
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

        ImportDeveloperCommand otherImportCommand = (ImportDeveloperCommand) other;
        return toAddList.equals(otherImportCommand.toAddList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddList", toAddList)
                .toString();
    }
}
