package seedu.address.model.developer;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCUMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;

import java.util.Objects;
import java.util.Set;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Represents a Developer in the address book, extending the Developer class.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Developer extends Person {
    private final Salary salary;
    private final Date dateJoined;
    private final GithubId githubId;
    private final Rating rating;
    private final DeveloperRoles role;

    public static final Prefix[] unusedPrefixes = new Prefix[]{PREFIX_ORGANISATION, PREFIX_DOCUMENT, PREFIX_DESCRIPTION,
            PREFIX_DEADLINE};

    /**
     * Every field must be present and not null.
     */
    public Developer(Name name, Phone phone, Email email, Address address, DeveloperRoles role, Set<String> projects,
                     Salary salary, Date dateJoined, GithubId githubId, Rating rating) {
        super(name, phone, email, address, projects);
        requireAllNonNull(salary, dateJoined, githubId, rating);
        this.salary = salary;
        this.dateJoined = dateJoined;
        this.githubId = githubId;
        this.rating = rating;
        this.role = role;
    }

    public Salary getSalary() {
        return salary;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public boolean isSameDeveloper(Developer otherDeveloper) {
        if (otherDeveloper == this) {
            return true;
        }

        return otherDeveloper != null
                && otherDeveloper.getName().equals(getName());
    }

    public GithubId getGithubId() {
        return githubId;
    }

    public Rating getRating() {
        return rating;
    }

    public DeveloperRoles getRole() {
        return role;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Developer)) {
            return false;
        }

        Developer otherDeveloper = (Developer) other;
        return super.equals(otherDeveloper) && salary.equals(otherDeveloper.salary)
                && dateJoined.equals(otherDeveloper.dateJoined)
                && githubId.equals(otherDeveloper.githubId)
                && rating.equals(otherDeveloper.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salary, dateJoined, githubId, rating);
    }

    @Override
    public String toString() {
        return super.toString() + " Salary: " + salary + " Date Joined: " + dateJoined
                + " Github ID: " + githubId + " Rating: " + rating;
    }
}