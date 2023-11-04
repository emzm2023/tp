package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;

/**
 * Adds a project to the address book.
 */
public class AddProjectCommand extends Command {

    public static final String COMMAND_WORD = "add-project";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the address book.\n"
            + Deadline.MESSAGE_CONSTRAINTS + "\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_DEADLINE + "DEADLINE_DATE,DEADLINE_DESCRIPTION,PRIORITY,IS_DONE]...\n"
            + "Example: \n" + COMMAND_WORD + " "
            + PREFIX_NAME + "JuiceApp "
            + PREFIX_DESCRIPTION + "App to allow for different juices to be ordered "
            + PREFIX_DEADLINE + "19-12-2023,Design backend,HIGH,0 "
            + PREFIX_DEADLINE + "25-12-2023,Design frontend,MEDIUM,0 ";

    public static final String MESSAGE_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the address book";

    private final Project toAdd;

    /**
     * Creates an AddProjectCommand to add the specified {@code Developer}
     */
    public AddProjectCommand(Project project) {
        requireNonNull(project);
        toAdd = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasProject(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        String successMessage = String.format(MESSAGE_SUCCESS, Messages.format(toAdd));
        TabIndex index = TabIndex.Project;

        model.addProject(toAdd);
        model.commitAddressBook(model, successMessage, index);
        return new CommandResult(successMessage, index);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddProjectCommand)) {
            return false;
        }

        AddProjectCommand otherAddProjectCommand = (AddProjectCommand) other;
        return toAdd.equals(otherAddProjectCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
