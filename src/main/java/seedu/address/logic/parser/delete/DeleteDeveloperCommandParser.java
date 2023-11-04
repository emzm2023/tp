package seedu.address.logic.parser.delete;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.delete.DeleteDeveloperCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteDeveloperCommand object
 */
public class DeleteDeveloperCommandParser implements Parser<DeleteDeveloperCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteDeveloperCommand
     * and returns a DeleteDeveloperCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDeveloperCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteDeveloperCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeveloperCommand.MESSAGE_USAGE), pe);
        }
    }

}
