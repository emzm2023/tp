package seedu.address.logic.parser.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEJOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCUMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUBID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.edit.EditDeveloperCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.developer.Developer;

/**
 * Parses input arguments and creates a new EditDeveloperCommand object
 */
public class EditDeveloperCommandParser implements Parser<EditDeveloperCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDeveloperCommand
     * and returns an EditDeveloperCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */

    public EditDeveloperCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_PROJECT, PREFIX_DATEJOINED, PREFIX_ROLE, PREFIX_SALARY, PREFIX_GITHUBID,
                PREFIX_RATING, PREFIX_ORGANISATION, PREFIX_DOCUMENT, PREFIX_DESCRIPTION, PREFIX_DEADLINE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditDeveloperCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.hasMappings()) {
            throw new ParseException(EditDeveloperCommand.MESSAGE_NOT_EDITED);
        }

        for (Prefix p : Developer.unusedPrefixes) {
            if (argMultimap.getValue(p).isPresent()) {
                throw new ParseException(String.format(Messages.MESSAGE_INAPPLICABLE_PREFIX_USED,
                        EditDeveloperCommand.MESSAGE_USAGE));
            }
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_DATEJOINED, PREFIX_ROLE, PREFIX_SALARY, PREFIX_GITHUBID, PREFIX_RATING);

        EditDeveloperCommand.EditDeveloperDescriptor editDeveloperDescriptor = new EditDeveloperCommand.EditDeveloperDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDeveloperDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editDeveloperDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editDeveloperDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editDeveloperDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DATEJOINED).isPresent()) {
            editDeveloperDescriptor.setDateJoined(ParserUtil.parseDateJoined(argMultimap.getValue(PREFIX_DATEJOINED).get()));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            editDeveloperDescriptor.setRole(ParserUtil.parseDeveloperRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }
        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            editDeveloperDescriptor.setSalary(ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get()));
        }
        if (argMultimap.getValue(PREFIX_GITHUBID).isPresent()) {
            editDeveloperDescriptor.setGithubId(ParserUtil.parseGithubId(argMultimap.getValue(PREFIX_GITHUBID).get()));
        }
        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            editDeveloperDescriptor.setRating(ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING).get()));
        }
        parseProjectsForEdit(argMultimap.getAllValues(PREFIX_PROJECT)).ifPresent(editDeveloperDescriptor::setProjects);

        if (!editDeveloperDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDeveloperCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDeveloperCommand(index, editDeveloperDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<String>} if {@code projects} is non-empty.
     * If {@code projects} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<String>} containing zero tags.
     */
    private Optional<Set<String>> parseProjectsForEdit(Collection<String> projects) throws ParseException {
        assert projects != null;

        if (projects.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> projectSet = projects.size() == 1 && projects.contains("") ? Collections.emptySet() : projects;
        return Optional.of(ParserUtil.parseProjectsToSet(projectSet));
    }
}
