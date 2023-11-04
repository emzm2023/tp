package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCUMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Adds a client to the address book.
 */
public class AddClientCommand extends Command {

    public static final String COMMAND_WORD = "add-client";
    //Name name, Phone phone, Email email, Address address, Role role, Set<String> projects,
    //                  Name organisation, Document document

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to the address book. "
            + "\n Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_ROLE + "ROLE "
            + "[" + PREFIX_PROJECT + "PROJECT]...\n"
            + PREFIX_ORGANISATION + "ORGANISATION "
            + PREFIX_DOCUMENT + "DOCUMENT \n"
            + "Example: \n" + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_ROLE + "Developer "
            + PREFIX_PROJECT + "AndroidApp "
            + PREFIX_PROJECT + "CustomWebsite "
            + PREFIX_ORGANISATION + "Google "
            + PREFIX_DOCUMENT + "google.com ";

    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists in the address book";

    private final Client toAdd;

    /**
     * Creates an AddDeveloperCommand to add the specified {@code Developer}
     */
    public AddClientCommand(Client client) {
        requireNonNull(client);
        toAdd = client;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        String res = model.areProjectsValid(toAdd);
        if (res != null) {
            throw new CommandException(String.format(Messages.MESSAGE_NONEXISTENT_PROJECT, res));
        }

        String successMessage = String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        TabIndex index = TabIndex.Client;

        model.addClient(toAdd);
        model.commitAddressBook(model, successMessage, index);
        return new CommandResult(successMessage, index);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddClientCommand)) {
            return false;
        }

        AddClientCommand otherAddClientCommand = (AddClientCommand) other;
        return toAdd.equals(otherAddClientCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
