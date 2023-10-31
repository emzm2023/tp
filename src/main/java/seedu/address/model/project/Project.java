package seedu.address.model.project;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.commons.Name;
import seedu.address.model.person.Person;

/**
 * Represents a Project in the address book.
 * Guarantees: immutable; fields are validated.
 */
public class Project {

    private final Name projectName;
    private final Description description;
    private final Set<Deadline> deadlines = new HashSet<>();
    private ObservableList<Deadline> deadlinesList;
    public static final Prefix[] unusedPrefixes = new Prefix[]{ PREFIX_DATEJOINED, PREFIX_SALARY, PREFIX_RATING,
            PREFIX_GITHUBID, PREFIX_ADDRESS, PREFIX_DOCUMENT, PREFIX_EMAIL, PREFIX_ORGANISATION, PREFIX_PHONE, 
            PREFIX_PROJECT, PREFIX_ROLE };

    public static final Prefix[] unusedPrefixesForEdit = new Prefix[]{ PREFIX_DATEJOINED, PREFIX_SALARY, PREFIX_RATING,
            PREFIX_GITHUBID, PREFIX_ADDRESS, PREFIX_DOCUMENT, PREFIX_EMAIL, PREFIX_ORGANISATION, PREFIX_PHONE,
            PREFIX_PROJECT, PREFIX_ROLE, PREFIX_NAME };

    /**
     * Constructs a {@code Tag}.
     *
     * @param projectName A valid project name.
     */
    public Project(Name projectName, Description desc, Set<Deadline> deadlines) {
        requireAllNonNull(projectName, desc, deadlines);
        this.projectName = projectName;
        this.description = desc;
        this.deadlines.addAll(deadlines);
        this.deadlinesList = FXCollections.observableList(deadlines.stream().collect(Collectors.toList()));
    }
    public Project(String projectName) {
        this(new Name(projectName),new Description(""),new HashSet<>());
    }
    public String getName() {
        return projectName.fullName;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Project)) {
            return false;
        }

        Project otherTag = (Project) other;
        return projectName.equals(otherTag.projectName)
                && description.equals(otherTag.description)
                && deadlines.equals(otherTag.deadlines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, description, deadlines);
    }

    /**
     * Returns the name of the Project.
     */
    @Override
    public String toString() {
        return projectName.toString();
    }
    
    public Name getProjectName() {
        return projectName;
    }

    public Description getProjectDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Deadline> getProjectDeadlines() {
        return Collections.unmodifiableSet(deadlines);
    }

    public ObservableList<Deadline> getProjectDeadlinesList() {
        return deadlinesList;
    }
    
    public void markDeadline(int index) {
        deadlinesList.get(index).mark();
    }

    public void unmarkDeadline(int index) {
        deadlinesList.get(index).unmark();
    }

    public int deadlineListSize() {
        return deadlinesList.size();
    }
    public boolean isSameProject(Project otherProject) {
        if (otherProject == this) {
            return true;
        }

        return otherProject != null
                && otherProject.getName().equals(getName());
    }

    /**
     * Returns true if a given String is a valid project name.
     * 
     * @param test The String to test.
     */
    public static boolean isValidProject(String test) {
        return true;
    }
}
