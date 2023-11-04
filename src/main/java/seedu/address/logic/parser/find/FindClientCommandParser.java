package seedu.address.logic.parser.find;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCUMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.find.FindClientCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.AddressClientContainsKeywordsPredicate;
import seedu.address.model.client.Client;
import seedu.address.model.client.DocumentContainsKeywordsPredicate;
import seedu.address.model.client.EmailClientContainsKeywordsPredicate;
import seedu.address.model.client.NameClientContainsKeywordsPredicate;
import seedu.address.model.client.OrganisationContainsKeywordsPredicate;
import seedu.address.model.client.PhoneClientContainsKeywordsPredicate;
import seedu.address.model.client.ProjectClientContainsKeywordsPredicate;
import seedu.address.model.client.RoleClientContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindClientCommand object
 */
public class FindClientCommandParser implements Parser<FindClientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindClientCommand
     * and returns a FindClientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindClientCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE, PREFIX_ADDRESS,
                PREFIX_EMAIL, PREFIX_PHONE, PREFIX_PROJECT, PREFIX_DOCUMENT, PREFIX_ORGANISATION);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientCommand.MESSAGE_USAGE));
        }

        Predicate<Client> finalPredicate = buildPredicate(argMultimap);

        return new FindClientCommand(finalPredicate);
    }

    private Predicate<Client> buildPredicate(ArgumentMultimap argMultimap) {
        Predicate<Client> finalPredicate = client -> true;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            finalPredicate = finalPredicate.and(new NameClientContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String[] roleKeywords = argMultimap.getValue(PREFIX_ROLE).get().split("\\s+");
            finalPredicate = finalPredicate.and(new RoleClientContainsKeywordsPredicate(Arrays.asList(roleKeywords)));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String[] addressKeywords = argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+");
            finalPredicate =
                    finalPredicate.and(new AddressClientContainsKeywordsPredicate(Arrays.asList(addressKeywords)));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            finalPredicate = finalPredicate.and(new EmailClientContainsKeywordsPredicate(Arrays.asList(emailKeywords)));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] phoneKeywords = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");
            finalPredicate = finalPredicate.and(new PhoneClientContainsKeywordsPredicate(Arrays.asList(phoneKeywords)));
        }

        if (argMultimap.getValue(PREFIX_PROJECT).isPresent()) {
            String[] projectKeywords = argMultimap.getValue(PREFIX_PROJECT).get().split("\\s+");
            finalPredicate =
                    finalPredicate.and(new ProjectClientContainsKeywordsPredicate(Arrays.asList(projectKeywords)));
        }

        if (argMultimap.getValue(PREFIX_DOCUMENT).isPresent()) {
            String[] documentKeywords = argMultimap.getValue(PREFIX_DOCUMENT).get().split("\\s+");
            finalPredicate = finalPredicate.and(new DocumentContainsKeywordsPredicate(Arrays.asList(documentKeywords)));
        }

        if (argMultimap.getValue(PREFIX_ORGANISATION).isPresent()) {
            String[] organisationKeywords = argMultimap.getValue(PREFIX_ORGANISATION).get().split("\\s+");
            finalPredicate =
                    finalPredicate.and(new OrganisationContainsKeywordsPredicate(Arrays.asList(organisationKeywords)));
        }
        return finalPredicate;
    }
}
