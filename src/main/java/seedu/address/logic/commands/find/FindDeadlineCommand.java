package seedu.address.logic.commands.find;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.getMessageProjectsListedOverview;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEJOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.model.Model;
import seedu.address.model.project.Deadline;

public class FindDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "find-deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter deadlines within projects.\n"
            + "Parameters: "
            + PREFIX_DATEJOINED + "DATE (Shows deadlines before this date) \n"
            + "[" + PREFIX_PRIORITY + "[HIGH/MEDIUM/LOW] (Shows deadlines based on priority)\n"
            + "Example: " + COMMAND_WORD + " d/2023-12-31 pri/HIGH\n";

    private final Predicate<Deadline> predicate;

    public FindDeadlineCommand(Predicate<Deadline> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredProjectDeadlineList(predicate);

        int resultCount = model.getFilteredProjectList().size();
        String message = getMessageProjectsListedOverview(resultCount);

        return new CommandResult(message, TabIndex.Project);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindDeadlineCommand)) {
            return false;
        }

        FindDeadlineCommand otherFindDeadlineCommand = (FindDeadlineCommand) other;
        return predicate.equals(otherFindDeadlineCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
