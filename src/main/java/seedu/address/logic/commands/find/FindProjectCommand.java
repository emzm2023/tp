package seedu.address.logic.commands.find;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.getMessageProjectsListedOverview;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindProjectCommand extends Command {

    public static final String COMMAND_WORD = "find-project";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find projects based on various attributes.\n"
            + "Parameters: "
            + "[" + PREFIX_PROJECT + "PROJECT_NAME_KEYWORDS] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION_KEYWORDS] "
            + "[" + PREFIX_DEADLINE + "DEADLINE_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " pr/MyProject\n";

    private Predicate<Project> predicate;

    public FindProjectCommand(Predicate<Project> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProjectList(predicate);

        int resultCount = model.getFilteredProjectList().size();
        String message = getMessageProjectsListedOverview(resultCount);

        return new CommandResult(message, TabIndex.Project);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindProjectCommand)) {
            return false;
        }

        FindProjectCommand otherFindProjectCommand = (FindProjectCommand) other;
        return predicate.equals(otherFindProjectCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
