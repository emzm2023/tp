package seedu.address.logic.commands.addRoles;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.add.AddDeveloperCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.developer.DeveloperRoles;

public class AddDeveloperRoleCommand extends Command {
    public static final String COMMAND_WORD = "add-developer-role";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a role for developers to the address book. "
            + "Parameters: " + PREFIX_ROLE + "ROLE "
            + "Example: " + PREFIX_ROLE + "Developer ";

    public static final String MESSAGE_SUCCESS = "New role for developer added: %1$s";
    public static final String MESSAGE_DUPLICATE_DEVELOPER = "This developer role already exists in the address book";
    private final String toAdd;

    /**
     * Creates an AddDeveloperRoleCommand to add the specified {@code Developer}
     */
    public AddDeveloperRoleCommand(String role) {
        requireNonNull(role);
        toAdd = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (DeveloperRoles.isValidRole(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEVELOPER);
        }

        String successMessage = String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        TabIndex index = TabIndex.Developer;

        DeveloperRoles newRole = new DeveloperRoles(toAdd.toString());
        DeveloperRoles.addDeveloperRole(newRole);
        return new CommandResult(successMessage, index);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddDeveloperCommand)) {
            return false;
        }

        AddDeveloperRoleCommand otherAddDeveloperRoleCommand = (AddDeveloperRoleCommand) other;
        return toAdd.equals(otherAddDeveloperRoleCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
