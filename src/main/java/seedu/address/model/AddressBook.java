package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.developer.Developer;
import seedu.address.model.developer.UniqueDeveloperList;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;
import seedu.address.model.project.UniqueProjectList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueDeveloperList developers;
    private final UniqueClientList clients;
    private final UniqueProjectList projects;

    {
        developers = new UniqueDeveloperList();
        clients = new UniqueClientList();
        projects = new UniqueProjectList();
    }

    public AddressBook() {
    }

    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /// Project Validation

    /**
     * Returns a boolean representing whether the projects assigned to the Person exist.
     *
     * @param person The person to check.
     */
    public String areProjectsValid(Person person) {
        Set<String> projects = person.getProjects();
        for (String p : projects) {
            if (!this.hasProject(p)) {
                return p;
            }
        }
        return null;
    }

    //// List Overwrite Operations

    public void setDevelopers(List<Developer> developers) {
        this.developers.setDevelopers(developers);
    }

    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
    }

    public void setProjects(List<seedu.address.model.project.Project> projects) {
        this.projects.setProjects(projects);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setDevelopers(newData.getDeveloperList());
        setClients(newData.getClientList());
        setProjects(newData.getProjectList());
    }

    //// Developer-level Operations

    public boolean hasDeveloper(Developer developer) {
        requireNonNull(developer);
        return developers.contains(developer);
    }

    public void addDeveloper(Developer developer) {
        developers.add(developer);
    }

    public void setDeveloper(Developer target, Developer editedDeveloper) {
        requireNonNull(editedDeveloper);
        developers.setDeveloper(target, editedDeveloper);
    }

    public void removeDeveloper(Developer key) {
        developers.remove(key);
    }

    //// Client-level Operations

    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void setClient(Client target, Client editedClient) {
        requireNonNull(editedClient);
        clients.setClient(target, editedClient);
    }

    public void removeClient(Client key) {
        clients.remove(key);
    }

    //// Project-level Operations

    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projects.contains(project);
    }

    public boolean hasProject(String project) {
        requireNonNull(project);
        return projects.contains(project);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void setProject(Project target, Project editedProject) {
        requireNonNull(editedProject);
        projects.setProject(target, editedProject);
    }

    public void removeProject(Project key) {
        projects.remove(key);
        clients.updateClientProjects(key.getName());
        developers.updateDeveloperProjects(key.getName());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("developers", developers).add("clients", clients).add("projects",
                projects).toString();
    }

    @Override
    public ObservableList<Developer> getDeveloperList() {
        return developers.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<seedu.address.model.project.Project> getProjectList() {
        return projects.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return developers.equals(otherAddressBook.developers) && clients.equals(otherAddressBook.clients)
                && projects.equals(otherAddressBook.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developers, clients, projects);
    }
}
