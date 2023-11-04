package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.commands.add.AddClientCommand;
import seedu.address.logic.commands.add.AddDeveloperCommand;
import seedu.address.logic.commands.add.AddProjectCommand;
import seedu.address.logic.commands.addRoles.AddClientRoleCommand;
import seedu.address.logic.commands.addRoles.AddDeveloperRoleCommand;
import seedu.address.logic.commands.delete.DeleteClientCommand;
import seedu.address.logic.commands.delete.DeleteDeveloperCommand;
import seedu.address.logic.commands.delete.DeleteProjectCommand;
import seedu.address.logic.commands.deleteRoles.DeleteClientRoleCommand;
import seedu.address.logic.commands.deleteRoles.DeleteDeveloperRoleCommand;
import seedu.address.logic.commands.edit.EditClientCommand;
import seedu.address.logic.commands.edit.EditDeveloperCommand;
import seedu.address.logic.commands.edit.EditProjectCommand;
import seedu.address.logic.commands.find.FindClientCommand;
import seedu.address.logic.commands.find.FindDeadlineCommand;
import seedu.address.logic.commands.find.FindDeveloperCommand;
import seedu.address.logic.commands.find.FindProjectCommand;
import seedu.address.logic.commands.imports.ImportClientCommand;
import seedu.address.logic.commands.imports.ImportDeveloperCommand;
import seedu.address.logic.commands.list.ListClientCommand;
import seedu.address.logic.commands.list.ListDeveloperCommand;
import seedu.address.logic.commands.list.ListProjectCommand;
import seedu.address.logic.commands.mark.MarkDeadlineCommand;
import seedu.address.logic.commands.mark.UnmarkDeadlineCommand;
import seedu.address.logic.parser.add.AddClientCommandParser;
import seedu.address.logic.parser.add.AddDeveloperCommandParser;
import seedu.address.logic.parser.add.AddProjectCommandParser;
import seedu.address.logic.parser.addRoles.AddClientRoleCommandParser;
import seedu.address.logic.parser.addRoles.AddDeveloperRoleCommandParser;
import seedu.address.logic.parser.delete.DeleteClientCommandParser;
import seedu.address.logic.parser.delete.DeleteDeveloperCommandParser;
import seedu.address.logic.parser.delete.DeleteProjectCommandParser;
import seedu.address.logic.parser.deleteRoles.DeleteClientRoleCommandParser;
import seedu.address.logic.parser.deleteRoles.DeleteDeveloperRoleCommandParser;
import seedu.address.logic.parser.edit.EditClientCommandParser;
import seedu.address.logic.parser.edit.EditDeveloperCommandParser;
import seedu.address.logic.parser.edit.EditProjectCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.find.FindClientCommandParser;
import seedu.address.logic.parser.find.FindDeadlineCommandParser;
import seedu.address.logic.parser.find.FindDeveloperCommandParser;
import seedu.address.logic.parser.find.FindProjectCommandParser;
import seedu.address.logic.parser.imports.ImportClientCommandParser;
import seedu.address.logic.parser.imports.ImportDeveloperCommandParser;
import seedu.address.logic.parser.mark.MarkDeadlineCommandParser;
import seedu.address.logic.parser.mark.UnmarkDeadlineCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);
    private static boolean isLocked = true;

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);
        if (isLocked == false) {
            switch (commandWord) {

            case AddDeveloperCommand.COMMAND_WORD:
                return new AddDeveloperCommandParser().parse(arguments);

            case AddClientCommand.COMMAND_WORD:
                return new AddClientCommandParser().parse(arguments);

            case AddProjectCommand.COMMAND_WORD:
                return new AddProjectCommandParser().parse(arguments);

            case AddDeveloperRoleCommand.COMMAND_WORD:
                return new AddDeveloperRoleCommandParser().parse(arguments);

            case AddClientRoleCommand.COMMAND_WORD:
                return new AddClientRoleCommandParser().parse(arguments);

            case ImportDeveloperCommand.COMMAND_WORD:
                return new ImportDeveloperCommandParser().parse(arguments);

            case ImportClientCommand.COMMAND_WORD:
                return new ImportClientCommandParser().parse(arguments);
            case FindDeadlineCommand.COMMAND_WORD:
                return new FindDeadlineCommandParser().parse(arguments);

            case ImportCommand.COMMAND_WORD:
                return new ImportDeveloperCommandParser().parse(arguments);

            case EditDeveloperCommand.COMMAND_WORD:
                return new EditDeveloperCommandParser().parse(arguments);

            case EditClientCommand.COMMAND_WORD:
                return new EditClientCommandParser().parse(arguments);

            case EditProjectCommand.COMMAND_WORD:
                return new EditProjectCommandParser().parse(arguments);

            case DeleteDeveloperCommand.COMMAND_WORD:
                return new DeleteDeveloperCommandParser().parse(arguments);

            case DeleteClientCommand.COMMAND_WORD:
                return new DeleteClientCommandParser().parse(arguments);

            case DeleteProjectCommand.COMMAND_WORD:
                return new DeleteProjectCommandParser().parse(arguments);

            case DeleteDeveloperRoleCommand.COMMAND_WORD:
                return new DeleteDeveloperRoleCommandParser().parse(arguments);

            case DeleteClientRoleCommand.COMMAND_WORD:
                return new DeleteClientRoleCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindDeveloperCommand.COMMAND_WORD:
                return new FindDeveloperCommandParser().parse(arguments);

            case FindClientCommand.COMMAND_WORD:
                return new FindClientCommandParser().parse(arguments);

            case FindProjectCommand.COMMAND_WORD:
                return new FindProjectCommandParser().parse(arguments);

            case ListClientCommand.COMMAND_WORD:
                return new ListClientCommand();

            case ListDeveloperCommand.COMMAND_WORD:
                return new ListDeveloperCommand();

            case ListProjectCommand.COMMAND_WORD:
                return new ListProjectCommand();

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case MarkDeadlineCommand.COMMAND_WORD:
                return new MarkDeadlineCommandParser().parse(arguments);

            case UnmarkDeadlineCommand.COMMAND_WORD:
                return new UnmarkDeadlineCommandParser().parse(arguments);

            case LockCommand.COMMAND_WORD:
                return new LockCommand();

            case ChangePasswordCommand.COMMAND_WORD:
                return new ChangePasswordCommandParser().parse(arguments);

            default:
                logger.finer("This user input caused a ParseException: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            switch (commandWord) {
            case UnlockCommand.COMMAND_WORD:
                return new UnlockCommandParser().parse(arguments);
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                logger.finer("This user input caused a ParseException: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }

    public static void lock() {
        isLocked = true;
    }

    public static void unlock() {
        isLocked = false;
    }
}
