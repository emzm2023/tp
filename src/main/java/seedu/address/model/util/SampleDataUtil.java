package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientRoles;
import seedu.address.model.client.Document;
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
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Description;
import seedu.address.model.project.Project;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Developer[] getSampleDevelopers() {
        Set<String> projectSet1 = getProjectSet("CodeContact", "TeamTrekker");
        Set<String> projectSet2 = getProjectSet("Orbital", "Appollo");
        Set<String> projectSet3 = getProjectSet("CodeContact");
        Set<String> projectSet4 = getProjectSet("TeamTrekker", "Appollo");

        return new Developer[]{
                new Developer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new DeveloperRoles("Developer"), projectSet1,
                        new Salary("5000"), new Date("15-12-2019"), new GithubId("mahidharah"), new Rating("5.0")),
                new Developer(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new DeveloperRoles("Developer"),
                        projectSet2,
                        new Salary("6000"), new Date("16-11-2020"), new GithubId("mahidharah1"), new Rating("5.0")),
                new Developer(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new DeveloperRoles("Developer"),
                        projectSet3,
                        new Salary("4500"), new Date("20-10-2020"), new GithubId("mahidharah2"), new Rating("5.0")),
                new Developer(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new DeveloperRoles("Developer"),
                        projectSet4,
                        new Salary("5500"), new Date("23-09-2021"), new GithubId("mahidharah3"), new Rating("5.0"))
        };
    }

    public static Client[] getSampleClients() {
        Set<String> projectSet1 = getProjectSet("CodeContact", "TeamTrekker");
        Set<String> projectSet2 = getProjectSet("Orbital", "Appollo");
        Set<String> projectSet3 = getProjectSet("CodeContact");
        Set<String> projectSet4 = getProjectSet("TeamTrekker", "Appollo");

        return new Client[]{
                new Client(new Name("Eva Tang"), new Phone("98765432"), new Email("eva@example.com"),
                        new Address("Blk 123 Bukit Batok Street 11, #01-01"), new ClientRoles("HR"), projectSet1,
                        new Name("XYZ Corp"), new Document("https://www.xyzcorp.com/")),
                new Client(new Name("Fiona Goh"), new Phone("87654321"), new Email("fiona@example.com"),
                        new Address("Blk 456 Jurong East Street 33, #02-02"), new ClientRoles("Manager"), projectSet2,
                        new Name("ABC Pte Ltd"), new Document("https://www.abc.com/")),
                new Client(new Name("George Lim"), new Phone("76543210"), new Email("george@example.com"),
                        new Address("Blk 789 Woodlands Ave 6, #03-03"), new ClientRoles("Developer"), projectSet3,
                        new Name("MNO Company"), new Document("https://www.mno.com/")),
                new Client(new Name("Helen Tan"), new Phone("65432109"), new Email("helen@example.com"),
                        new Address("Blk 321 Tampines Street 33, #04-04"), new ClientRoles("HR"), projectSet4,
                        new Name("PQR LLC"), new Document("https://www.pqr.com/"))
        };
    }

    public static Project[] getSampleProjects() {
        return new Project[]{
                new Project(new Name("CodeContact"), new Description("A contact management system"), getDeadlineList(
                        "13-10-2021,Phase 1,HIGH,0")),
                new Project(new Name("TeamTrekker"), new Description("A team collaboration tool"), getDeadlineList(
                        "13-11-2021,Phase 2,MEDIUM,0")),
                new Project(new Name("Orbital"), new Description("A satellite tracking system"), getDeadlineList("13" +
                        "-12-2021,Phase 3,LOW,0")),
                new Project(new Name("Appollo"), new Description("A mobile app development platform"),
                        getDeadlineList("13-01-2022,Launch,HIGH,0"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Developer sampleDeveloper : getSampleDevelopers()) {
            sampleAb.addDeveloper(sampleDeveloper);
        }
        for (Client sampleClient : getSampleClients()) {
            sampleAb.addClient(sampleClient);
        }
        for (Project sampleProject : getSampleProjects()) {
            sampleAb.addProject(sampleProject);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<String> getProjectSet(String... strings) {
        return Arrays.stream(strings)
                .collect(Collectors.toSet());
    }

    public static List<Deadline> getDeadlineList(String... deadlines) {
        List<Deadline> deadlineList = new ArrayList<>();
        for (String s : deadlines) {
            deadlineList.add(new Deadline(s, deadlineList.size() + 1));
        }
        return deadlineList;
    }
}
