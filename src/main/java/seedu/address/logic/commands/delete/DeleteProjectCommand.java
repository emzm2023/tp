package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

/**
 * Deletes a project identified using it's displayed index from the address book.
 */
public class DeleteProjectCommand extends Command {

    public static final String COMMAND_WORD = "delete-project";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the project identified by the index number used in the displayed project list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project: %1$s";

    private final Index targetIndex;

    public DeleteProjectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DEVELOPER_DISPLAYED_INDEX);
        }

        Project projectToDelete = lastShownList.get(targetIndex.getZeroBased());
        String successMessage = String.format(MESSAGE_DELETE_PROJECT_SUCCESS, Messages.format(projectToDelete));
        TabIndex index = TabIndex.Project;
        model.deleteProject(projectToDelete);
        model.commitAddressBook(model, successMessage, index);
        return new CommandResult(successMessage, index);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteProjectCommand)) {
            return false;
        }

        DeleteProjectCommand otherDeleteDeveloperCommand = (DeleteProjectCommand) other;
        return targetIndex.equals(otherDeleteDeveloperCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
