package seedu.address.logic.parser.find;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEJOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUBID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.find.FindDeveloperCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.developer.AddressDeveloperContainsKeywordsPredicate;
import seedu.address.model.developer.DateJoinedContainsKeywordsPredicate;
import seedu.address.model.developer.Developer;
import seedu.address.model.developer.EmailDeveloperContainsKeywordsPredicate;
import seedu.address.model.developer.GithubIdContainsKeywordsPredicate;
import seedu.address.model.developer.NameDeveloperContainsKeywordsPredicate;
import seedu.address.model.developer.PhoneDeveloperContainsKeywordsPredicate;
import seedu.address.model.developer.ProjectDeveloperContainsKeywordsPredicate;
import seedu.address.model.developer.RatingContainsKeywordsPredicate;
import seedu.address.model.developer.RoleDeveloperContainsKeywordsPredicate;
import seedu.address.model.developer.SalaryContainsKeywordsPredicate;

public class FindDeveloperCommandParser implements Parser<FindDeveloperCommand> {
    public FindDeveloperCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeveloperCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE, PREFIX_ADDRESS,
                PREFIX_EMAIL, PREFIX_GITHUBID, PREFIX_DATEJOINED, PREFIX_PROJECT, PREFIX_PHONE, PREFIX_SALARY, PREFIX_RATING);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeveloperCommand.MESSAGE_USAGE));
        }

        Predicate<Developer> finalPredicate = buildPredicate(argMultimap);

        return new FindDeveloperCommand(finalPredicate);
    }

    private Predicate<Developer> buildPredicate(ArgumentMultimap argMultimap) {
        Predicate<Developer> finalPredicate = developer -> true;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            finalPredicate = finalPredicate.and(new NameDeveloperContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String[] roleKeywords = argMultimap.getValue(PREFIX_ROLE).get().split("\\s+");
            finalPredicate = finalPredicate.and(new RoleDeveloperContainsKeywordsPredicate(Arrays.asList(roleKeywords)));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String[] addressKeywords = argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+");
            finalPredicate = finalPredicate.and(new AddressDeveloperContainsKeywordsPredicate(Arrays.asList(addressKeywords)));
        }

        if (argMultimap.getValue(PREFIX_DATEJOINED).isPresent()) {
            String[] dateJoinedKeywords = argMultimap.getValue(PREFIX_DATEJOINED).get().split("\\s+");
            finalPredicate = finalPredicate.and(new DateJoinedContainsKeywordsPredicate(Arrays.asList(dateJoinedKeywords)));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            finalPredicate = finalPredicate.and(new EmailDeveloperContainsKeywordsPredicate(Arrays.asList(emailKeywords)));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] phoneKeywords = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");
            finalPredicate = finalPredicate.and(new PhoneDeveloperContainsKeywordsPredicate(Arrays.asList(phoneKeywords)));
        }

        if (argMultimap.getValue(PREFIX_PROJECT).isPresent()) {
            String[] projectKeywords = argMultimap.getValue(PREFIX_PROJECT).get().split("\\s+");
            finalPredicate = finalPredicate.and(new ProjectDeveloperContainsKeywordsPredicate(Arrays.asList(projectKeywords)));
        }

        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            String[] salaryKeywords = argMultimap.getValue(PREFIX_SALARY).get().split("\\s+");
            finalPredicate = finalPredicate.and(new SalaryContainsKeywordsPredicate(Arrays.asList(salaryKeywords)));
        }

        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            String[] ratingKeywords = argMultimap.getValue(PREFIX_RATING).get().split("\\s+");
            finalPredicate = finalPredicate.and(new RatingContainsKeywordsPredicate(Arrays.asList(ratingKeywords)));
        }

        if (argMultimap.getValue(PREFIX_GITHUBID).isPresent()) {
            String[] githubKeywords = argMultimap.getValue(PREFIX_GITHUBID).get().split("\\s+");
            finalPredicate = finalPredicate.and(new GithubIdContainsKeywordsPredicate(Arrays.asList(githubKeywords)));
        }

        return finalPredicate;
    }
}
