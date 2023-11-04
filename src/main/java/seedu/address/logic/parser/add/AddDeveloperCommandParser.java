package seedu.address.logic.parser.add;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.add.AddDeveloperCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.developer.Developer;
import seedu.address.model.developer.DeveloperRoles;
import seedu.address.model.developer.GithubId;
import seedu.address.model.developer.Rating;
import seedu.address.model.developer.Salary;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new AddDeveloperCommand object
 */
public class AddDeveloperCommandParser implements Parser<AddDeveloperCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDeveloperCommand
     * and returns an AddDeveloperCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDeveloperCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_PROJECT, PREFIX_DATEJOINED, PREFIX_ROLE, PREFIX_SALARY, PREFIX_GITHUBID,
                PREFIX_RATING, PREFIX_ORGANISATION, PREFIX_DOCUMENT, PREFIX_DESCRIPTION, PREFIX_DEADLINE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_SALARY, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeveloperCommand.MESSAGE_USAGE));
        }

        for (Prefix p : Developer.unusedPrefixes) {
            if (argMultimap.getValue(p).isPresent()) {
                throw new ParseException(String.format(Messages.MESSAGE_INAPPLICABLE_PREFIX_USED,
                        AddDeveloperCommand.MESSAGE_USAGE));
            }
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Date dateJoined = ParserUtil.parseDateJoined(argMultimap.getValue(PREFIX_DATEJOINED)
                .orElse(new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date())));
        DeveloperRoles role = ParserUtil.parseDeveloperRole(argMultimap.getValue(PREFIX_ROLE).get());
        Salary salary = ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get());
        Set<String> projectList = ParserUtil.parseProjectsToSet(argMultimap.getAllValues(PREFIX_PROJECT));
        GithubId githubId = ParserUtil.parseGithubId(argMultimap.getValue(PREFIX_GITHUBID).orElse(""));
        Rating rating = ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING).orElse("0"));

        Developer developer = new Developer(name, phone, email, address, role, projectList, salary,
                dateJoined, githubId, rating);

        return new AddDeveloperCommand(developer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
