package seedu.address.logic.parser.mark;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.mark.UnmarkDeadlineCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkDeadlineCommand object.
 */
public class UnmarkDeadlineCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkDeadlineCommand
     * and returns a UnmarkDeadlineCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public UnmarkDeadlineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] input = args.trim().split(" ");

        if (input.length != 2) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkDeadlineCommand.MESSAGE_USAGE));
        }

        Index projIndex;
        Index deadlineIndex;

        try {
            projIndex = ParserUtil.parseIndex(input[0]);
            deadlineIndex = ParserUtil.parseIndex(input[1]);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkDeadlineCommand.MESSAGE_USAGE));
        }

        return new UnmarkDeadlineCommand(projIndex, deadlineIndex);
    }
}