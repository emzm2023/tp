package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Password;
import seedu.address.model.client.ClientRoles;
import seedu.address.model.client.Document;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.developer.DeveloperRoles;
import seedu.address.model.developer.GithubId;
import seedu.address.model.developer.Rating;
import seedu.address.model.developer.Salary;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Description;
import seedu.address.model.project.Project;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String project} into a {@code Project}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Project} is invalid.
     */
    public static Project parseProject(String project) throws ParseException {
        requireNonNull(project);
        String trimmedProject = project.trim();
        if (!Name.isValidName(trimmedProject)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Project(new Name(trimmedProject), new Description(""), new ArrayList<Deadline>());
    }

    /**
     * Parses {@code Collection<String> projects} into a {@code Set<project>}.
     */
    public static Set<Project> parseProjects(Collection<String> projects) throws ParseException {
        requireNonNull(projects);
        final Set<Project> projectSet = new HashSet<>();
        for (String projectName : projects) {
            projectSet.add(parseProject(projectName));
        }
        return projectSet;
    }

    /**
     * Parses {@code Collection<String> projects} into a {@code Set<String>}.
     *
     * @param projects The Collection of projects to parse.
     * @returns A HashSet of String.
     */
    public static Set<String> parseProjectsToSet(Collection<String> projects) {
        requireNonNull(projects);
        final Set<String> projectSet = new HashSet<>();

        for (String p : projects) {
            projectSet.add(p);
        }
        return projectSet;
    }

    /**
     * Parses {@code Collection<String> deadlines} into a {@code List<Deadline>}.
     *
     * @param deadlines The Collection of deadlines to parse.
     * @throws ParseException if format is invalid.
     * @returns An ArrayList of Deadlines if parsing is successful.
     */
    public static List<Deadline> parseDeadlines(Collection<String> deadlines) throws ParseException {
        requireNonNull(deadlines);
        final List<Deadline> deadlineSet = new ArrayList<>();
        for (String str : deadlines) {
            if (!Deadline.isValidDeadline(str)) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        Deadline.MESSAGE_CONSTRAINTS));
            } else {
                deadlineSet.add(new Deadline(str, deadlineSet.size() + 1));
            }
        }
        return deadlineSet;
    }

    /**
     * Parses a {@code String dateJoined} into a {@code DateJoined}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DateJoined} is invalid.
     */
    public static Date parseDateJoined(String dateJoined) throws ParseException {
        requireNonNull(dateJoined);
        String trimmedDateJoined = dateJoined.trim();
        if (!Date.isValidDate(trimmedDateJoined)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDateJoined);
    }


    /**
     * Parses a {@code String role} into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Role} is invalid.
     */
    public static DeveloperRoles parseDeveloperRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!DeveloperRoles.isValidRole(trimmedRole)) {
            throw new ParseException(DeveloperRoles.NO_SUCH_DEVELOPER_ROLE);
        }
        return new DeveloperRoles(trimmedRole);
    }

    public static ClientRoles parseClientRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!ClientRoles.isValidRole(trimmedRole)) {
            throw new ParseException(ClientRoles.NO_SUCH_CLIENT_ROLE);
        }
        return new ClientRoles(trimmedRole);
    }

    /**
     * Parses a {@code String salary} into a {@code Salary}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Salary} is invalid.
     */
    public static Salary parseSalary(String salary) throws ParseException {
        requireNonNull(salary);
        String trimmedSalary = salary.trim();
        if (!Salary.isValidSalary(trimmedSalary)) {
            throw new ParseException(Salary.MESSAGE_CONSTRAINTS);
        }
        return new Salary(trimmedSalary);
    }

    public static GithubId parseGithubId(String githubid) throws ParseException {
        requireNonNull(githubid);
        String trimmedGithubId = githubid.trim();
        if (!GithubId.isValidGithubId(trimmedGithubId)) {
            throw new ParseException(GithubId.MESSAGE_CONSTRAINTS);
        }
        return new GithubId(trimmedGithubId);
    }

    public static Rating parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        if (!Rating.isValidRating(trimmedRating)) {
            throw new ParseException(Salary.MESSAGE_CONSTRAINTS);
        }
        return new Rating(trimmedRating);
    }

    public static Document parseDocument(String doc) throws ParseException {
        requireNonNull(doc);
        String trimmedDoc = doc.trim();
        if (!Document.isValidUrl(trimmedDoc)) {
            throw new ParseException(Document.MESSAGE_CONSTRAINTS);
        }
        return new Document(trimmedDoc);
    }

    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    public static String parsePassword(String pw) throws ParseException {
        requireNonNull(pw);
        String trimmedPw = pw.trim();
        if (!Password.isValidPassword(trimmedPw)) {
            throw new ParseException(Password.MESSAGE_CONSTRAINTS);
        }
        return trimmedPw;
    }
}
