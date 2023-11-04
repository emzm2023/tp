package seedu.address.testutil;

import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.developer.*;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Developer objects.
 */
public class DeveloperBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE_JOINED = "01-01-2022";
    public static final String DEFAULT_ROLE = "Developer";
    public static final String DEFAULT_SALARY = "50000";
    public static final String DEFAULT_GITHUBID = "mahidharah";
    public static final String DEFAULT_RATING = "5.0";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private DeveloperRoles role;
    private Set<String> projects;
    private Salary salary;
    private Date dateJoined;
    private GithubId githubId;
    private Rating rating;


    public DeveloperBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        projects = new HashSet<>();
        dateJoined = new Date(DEFAULT_DATE_JOINED);
        role = new DeveloperRoles(DEFAULT_ROLE);
        salary = new Salary(DEFAULT_SALARY);
        githubId = new GithubId(DEFAULT_GITHUBID);
        rating = new Rating(DEFAULT_RATING);
    }

    public DeveloperBuilder(Developer developerToCopy) {
        name = developerToCopy.getName();
        phone = developerToCopy.getPhone();
        email = developerToCopy.getEmail();
        address = developerToCopy.getAddress();
        projects = new HashSet<>(developerToCopy.getProjects());
        dateJoined = developerToCopy.getDateJoined();
        role = developerToCopy.getRole();
        salary = developerToCopy.getSalary();
        githubId = developerToCopy.getGithubId();
        rating = developerToCopy.getRating();
    }

    public DeveloperBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public DeveloperBuilder withProjects(String... tags) {
        this.projects = SampleDataUtil.getProjectSet(tags);
        return this;
    }

    public DeveloperBuilder withGithubId(String githubId) {
        this.githubId = new GithubId(githubId);
        return this;
    }

    public DeveloperBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    public DeveloperBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    public DeveloperBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public DeveloperBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public DeveloperBuilder withDateJoined(String dateJoined) {
        this.dateJoined = new Date(dateJoined);
        return this;
    }

    public DeveloperBuilder withRole(String role) {
        this.role = new DeveloperRoles(role);
        return this;
    }

    public DeveloperBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    public Developer build() {
        return new Developer(name, phone, email, address, role, projects, salary, dateJoined, githubId, rating);
    }
}
