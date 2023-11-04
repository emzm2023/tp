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
import seedu.address.model.client.ClientRoles;

public class AddClientRoleCommand extends Command {
    public static final String COMMAND_WORD = "add-client-role";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Adds a role for clients to the address book. " + "Parameters: " + PREFIX_ROLE + "ROLE "
                    + "Example: " + PREFIX_ROLE + "Developer ";

    public static final String MESSAGE_SUCCESS = "New role for client added: %1$s";
    public static final String MESSAGE_DUPLICATE_DEVELOPER = "This client role already exists in the address book";
    private final String toAdd;

    /**
     * Creates an AddDeveloperRoleCommand to add the specified {@code Developer}
     */
    public AddClientRoleCommand(String role) {
        requireNonNull(role);
        toAdd = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (ClientRoles.isValidRole(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEVELOPER);
        }

        String successMessage = String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        TabIndex index = TabIndex.Client;

        ClientRoles newRole = new ClientRoles(toAdd.toString());
        ClientRoles.addClientRole(newRole);
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

        AddClientRoleCommand otherAddClientRoleCommand = (AddClientRoleCommand) other;
        return toAdd.equals(otherAddClientRoleCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }
}
