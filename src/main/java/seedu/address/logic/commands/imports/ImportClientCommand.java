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
import seedu.address.model.client.Client;

public class ImportClientCommand extends Command {
    public static final String COMMAND_WORD = "import-client";
    //Name name, Phone phone, Email email, Address address, Role role, Set<String> projects,
    //                  Name organisation, Document document

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Imports clients from csv file.\n" + "Column titles should follow this format strictly:\n"
                    + "Name, Contact Number, Email, Address, Role, Organisation, Document, Projects";
    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = " is a client that already exists in the address book\n";

    private final ArrayList<Client> toAddList;

    /**
     * Creates an AddClientCommand to add the specified {@code Client}
     */
    public ImportClientCommand(ArrayList<Client> clientList) {
        requireNonNull(clientList);
        for (Client i : clientList) {
            requireNonNull(i);
        }
        toAddList = clientList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String output = "";
        for (Client toAdd : toAddList) {
            if (model.hasClient(toAdd)) {
                output += toAdd.getName().fullName + MESSAGE_DUPLICATE_CLIENT;
            } else {
                model.addClient(toAdd);
                output += String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
            }
            output += "\n";
        }
        return new CommandResult(output, TabIndex.Client);
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

        ImportClientCommand otherImportCommand = (ImportClientCommand) other;
        return toAddList.equals(otherImportCommand.toAddList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAddList", toAddList).toString();
    }
}
