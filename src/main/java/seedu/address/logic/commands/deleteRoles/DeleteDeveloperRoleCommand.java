package seedu.address.logic.commands.deleteRoles;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.delete.DeleteClientCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.developer.DeveloperRoles;

public class DeleteDeveloperRoleCommand extends Command {
    public static final String COMMAND_WORD = "delete-developer-role";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete a role for developers in the address book. "
            + "Parameters: " + PREFIX_ROLE + "ROLE "
            + "Example: " + PREFIX_ROLE + "Developer ";

    public static final String MESSAGE_SUCCESS = "Role for developers deleted: %1$s";
    public static final String MESSAGE_CANNOT_DELETE_REPEAT = "This developer role cannot be deleted "
            + "as there are developers of this role";
    public static final String MESSAGE_CANNOT_DELETE_PREXISTS = "You are not allowed to delete this developer role.";
    public static final String MESSAGE_CANNOT_DELETE_NONEXISTING = "This developer role does not exist. ";
    public static final String MESSAGE_EXISTING_DEVELOPERS_ROLES = "These are the existing developer roles: \n";

    private final String toAdd;

    /**
     * Creates an AddDeveloperRoleCommand to add the specified {@code Developer}
     */
    public DeleteDeveloperRoleCommand(String role) {
        requireNonNull(role);
        toAdd = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (DeveloperRoles.isRemovableRole(model, toAdd)) {
            if (!DeveloperRoles.isNoRepeat()) {
                throw new CommandException(MESSAGE_CANNOT_DELETE_REPEAT);
            } else if (!DeveloperRoles.isNotDefault()) {
                throw new CommandException(MESSAGE_CANNOT_DELETE_PREXISTS);
            } else if (DeveloperRoles.isNotInList()) {
                throw new CommandException(MESSAGE_CANNOT_DELETE_NONEXISTING + MESSAGE_EXISTING_DEVELOPERS_ROLES
                        + DeveloperRoles.printRoles());
            }
        }

        String successMessage = String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        TabIndex index = TabIndex.Developer;

        DeveloperRoles newRole = new DeveloperRoles(toAdd.toString());
        DeveloperRoles.deleteDeveloperRole(newRole);
        return new CommandResult(successMessage, index);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteClientCommand)) {
            return false;
        }

        DeleteDeveloperRoleCommand otherDeleteDeveloperRoleCommand = (DeleteDeveloperRoleCommand) other;
        return toAdd.equals(otherDeleteDeveloperRoleCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
