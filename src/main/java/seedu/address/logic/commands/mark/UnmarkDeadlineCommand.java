package seedu.address.logic.commands.mark;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.edit.EditProjectCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;

public class UnmarkDeadlineCommand extends Command {
    public static final String COMMAND_WORD = "unmark-deadline";
    public static final String MESSAGE_SUCCESS = "The deadline has been marked as undone!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the specified deadline of the specified project "
            + "as undone. \n"
            + "DEADLINE_INDEX must be a positive integer representing the index of the deadline in the displayed "
            + "deadline table, and PROJECT_INDEX must be a positive integer which is the project's index number in the "
            + "displayed project list. \n"
            + "Parameters: "
            + "PROJECT_INDEX DEADLINE_INDEX \n"
            + "DEADLINE_INDEX (must be a positive integer, representing the index of the deadline in the "
            + "displayed deadline table) "
            + "Example: " + COMMAND_WORD + " 1 2";

    private final int deadlineIndex;
    private final Index projIndex;

    public UnmarkDeadlineCommand(int deadlineIndex, Index projIndex) {
        this.deadlineIndex = deadlineIndex;
        this.projIndex = projIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();
        if (projIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(projIndex.getZeroBased());

        if (deadlineIndex >= projectToEdit.deadlineListSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX);
        }

        projectToEdit.unmarkDeadline(deadlineIndex-1);
        return new CommandResult(MESSAGE_SUCCESS, TabIndex.Project);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkDeadlineCommand)) {
            return false;
        }

        UnmarkDeadlineCommand otherUnmarkDeadlineCommand = (UnmarkDeadlineCommand) other;
        return deadlineIndex == otherUnmarkDeadlineCommand.deadlineIndex
                && projIndex.equals(otherUnmarkDeadlineCommand.projIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("deadlineIndex", deadlineIndex)
                .add("projIndex", projIndex)
                .toString();
    }
}
