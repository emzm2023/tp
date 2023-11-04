package seedu.address.logic.parser.find;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEJOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.function.Predicate;

import seedu.address.logic.commands.find.FindDeadlineCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Date;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Priority;

public class FindDeadlineCommandParser implements Parser<FindDeadlineCommand> {

    public FindDeadlineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeadlineCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATEJOINED, PREFIX_PRIORITY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeadlineCommand.MESSAGE_USAGE));
        }

        Predicate<Deadline> finalPredicate = buildPredicate(argMultimap);

        return new FindDeadlineCommand(finalPredicate);
    }

    private Predicate<Deadline> buildPredicate(ArgumentMultimap argMultimap) {
        Predicate<Deadline> finalPredicate = deadline -> true;

        if (argMultimap.getValue(PREFIX_DATEJOINED).isPresent()) {
            String dateKeywords = argMultimap.getValue(PREFIX_DATEJOINED).get();
            Date input = new Date(dateKeywords);
            finalPredicate = finalPredicate.and(d -> !d.getDate().value.after(input.value)); // Replace with your 
            // DateJoinedPredicate
        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            String priorityKeywords = argMultimap.getValue(PREFIX_PRIORITY).get();
            Priority input = Priority.valueOf(priorityKeywords);
            finalPredicate = finalPredicate.and(d -> d.getPriority().equals(input)); // Replace with your 
            // PriorityPredicate
        }

        return finalPredicate;
    }
}
