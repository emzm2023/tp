package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.Client.Client;
import seedu.address.model.developer.Developer;
import seedu.address.model.project.Project;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_FILE = "File does not exist!\n";
    public static final String MESSAGE_INVALID_DEVELOPER_DISPLAYED_INDEX = "The developer index provided is invalid";
    public static final String MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX = "The client index provided is invalid";
    public static final String MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX = "The project index provided is invalid";

    public static final String MESSAGE_DEVELOPERS_LISTED_OVERVIEW =
                "These are the %1$d developers with matching information";
    public static final String MESSAGE_CLIENTS_LISTED_OVERVIEW =
            "These are the %1$d clients with matching information";
    public static final String MESSAGE_PROJECTS_LISTED_OVERVIEW =
            "These are the %1$d projects with matching information";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code developer} for display to the user.
     */
    public static String format(Developer developer) {
        final StringBuilder builder = new StringBuilder();
        builder.append(developer.getName())
                .append("; Phone: ")
                .append(developer.getPhone())
                .append("; Email: ")
                .append(developer.getEmail())
                .append("; Address: ")
                .append(developer.getAddress())
                .append("; Date Joined: ")
                .append(developer.getDateJoined())
                .append("; Role: ")
                .append(developer.getRole())
                .append("; Salary: ")
                .append(developer.getSalary())
                .append("; Projects: ");
        developer.getProjects().forEach(builder::append);
        return builder.toString();
    }

    public static String format(Client client) {
        final StringBuilder builder = new StringBuilder();
        builder.append(client.getName())
                .append("; Phone: ")
                .append(client.getPhone())
                .append("; Email: ")
                .append(client.getEmail())
                .append("; Address: ")
                .append(client.getAddress())
                .append("; Organisation: ")
                .append(client.getOrganisation())
                .append("; Role: ")
                .append(client.getRole())
                .append("; Document: ")
                .append(client.getDocument())
                .append("; Projects: ");
        client.getProjects().forEach(builder::append);
        return builder.toString();
    }

    public static Object format(Project project) {
        final StringBuilder builder = new StringBuilder();
        builder.append(project.getName())
                .append(";\nDescription: ")
                .append(project.getProjectDescription().get())
                .append(";\nDeadlines:\n");
        project.getProjectDeadlines().forEach(builder::append);
        return builder.toString();
    }
}
