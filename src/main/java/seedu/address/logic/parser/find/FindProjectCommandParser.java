package seedu.address.logic.parser.find;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.find.FindProjectCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.DeadlineContainsKeywordsPredicate;
import seedu.address.model.project.DescriptionContainsKeywordsPredicate;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindProjectCommand object
 */
public class FindProjectCommandParser implements Parser<FindProjectCommand> {

    public FindProjectCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProjectCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE, PREFIX_DESCRIPTION,
                PREFIX_PROJECT);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProjectCommand.MESSAGE_USAGE));
        }

        Predicate<Project> finalPredicate = buildPredicate(argMultimap);

        return new FindProjectCommand(finalPredicate);
    }

    private Predicate<Project> buildPredicate(ArgumentMultimap argMultimap) {
        Predicate<Project> finalPredicate = project -> true;

        if (argMultimap.getValue(PREFIX_PROJECT).isPresent()) {
            String[] projectNameKeywords = argMultimap.getValue(PREFIX_PROJECT).get().split("\\s+");
            finalPredicate =
                    finalPredicate.and(new ProjectNameContainsKeywordsPredicate(Arrays.asList(projectNameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            String[] descriptionKeywords = argMultimap.getValue(PREFIX_DESCRIPTION).get().split("\\s+");
            finalPredicate =
                    finalPredicate.and(new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)));
        }

        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            String[] deadlineKeywords = argMultimap.getValue(PREFIX_DEADLINE).get().split("\\s+");
            finalPredicate = finalPredicate.and(new DeadlineContainsKeywordsPredicate(Arrays.asList(deadlineKeywords)));
        }

        return finalPredicate;
    }
}
