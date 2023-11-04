package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing developer in the address book.
 */
public class EditDeveloperCommand extends Command {

    public static final String COMMAND_WORD = "edit-developer";
    public static final String MESSAGE_EDIT_DEVELOPER_SUCCESS = "Edited Developer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_DEVELOPER = "The details of the developer in the address book are already as given.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the developer identified "
            + "by the index number used in the displayed developer list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_PROJECT + "PROJECT]...\n"
            + "[" + PREFIX_SALARY + "SALARY] "
            + "[" + PREFIX_DATEJOINED + "DATE JOINED] "
            + "[" + PREFIX_GITHUBID + "GITHUBID] "
            + "[" + PREFIX_RATING + "RATING] "
            + "Example: \n" + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final Index index;

    private final EditDeveloperDescriptor editDeveloperDescriptor;


    /**
     * @param index                   of the developer in the filtered developer list to edit
     * @param editDeveloperDescriptor details to edit the developer with
     */
    public EditDeveloperCommand(Index index, EditDeveloperDescriptor editDeveloperDescriptor) {
        requireNonNull(index);
        requireNonNull(editDeveloperDescriptor);

        this.index = index;
        this.editDeveloperDescriptor = new EditDeveloperDescriptor(editDeveloperDescriptor);
    }

    /**
     * Creates and returns a {@code Developer} with the details of {@code developerToEdit}
     * edited with {@code editDeveloperDescriptor}.
     */
    static Developer createEditedDeveloper(Developer developerToEdit, EditDeveloperDescriptor editDeveloperDescriptor) {
        assert developerToEdit != null;

        Name updatedName = editDeveloperDescriptor.getName().orElse(developerToEdit.getName());
        Phone updatedPhone = editDeveloperDescriptor.getPhone().orElse(developerToEdit.getPhone());
        Email updatedEmail = editDeveloperDescriptor.getEmail().orElse(developerToEdit.getEmail());
        Address updatedAddress = editDeveloperDescriptor.getAddress().orElse(developerToEdit.getAddress());
        Date updatedDateJoined = editDeveloperDescriptor.getDateJoined().orElse(developerToEdit.getDateJoined());
        DeveloperRoles updatedRole = editDeveloperDescriptor.getRole().orElse(developerToEdit.getRole());
        Salary updatedSalary = editDeveloperDescriptor.getSalary().orElse(developerToEdit.getSalary());
        Set<String> updatedProjects = editDeveloperDescriptor.getProjects().orElse(developerToEdit.getProjects());
        GithubId updatedGithubId = editDeveloperDescriptor.getGithubId().orElse(developerToEdit.getGithubId());
        Rating updatedRating = editDeveloperDescriptor.getRating().orElse(developerToEdit.getRating());

        return new Developer(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedRole, updatedProjects, updatedSalary, updatedDateJoined, updatedGithubId, updatedRating);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Developer> lastShownList = model.getFilteredDeveloperList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DEVELOPER_DISPLAYED_INDEX);
        }

        Developer developerToEdit = lastShownList.get(index.getZeroBased());
        Developer editedDeveloper = createEditedDeveloper(developerToEdit, editDeveloperDescriptor);

        if (!developerToEdit.isSameDeveloper(editedDeveloper) && model.hasDeveloper(editedDeveloper)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEVELOPER);
        }
        String res = model.areProjectsValid(editedDeveloper);
        if (res != null) {
            throw new CommandException(String.format(Messages.MESSAGE_NONEXISTENT_PROJECT, res));
        }

        String successMessage = String.format(MESSAGE_EDIT_DEVELOPER_SUCCESS, Messages.format(editedDeveloper));
        TabIndex index = TabIndex.Developer;

        model.setDeveloper(developerToEdit, editedDeveloper);
        model.updateFilteredDeveloperList(Model.PREDICATE_SHOW_ALL_DEVELOPERS);
        model.commitAddressBook(model, successMessage, index);
        return new CommandResult(successMessage, index);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDeveloperCommand)) {
            return false;
        }

        EditDeveloperCommand otherEditDeveloperCommand = (EditDeveloperCommand) other;
        return index.equals(otherEditDeveloperCommand.index)
                && editDeveloperDescriptor.equals(otherEditDeveloperCommand.editDeveloperDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editDeveloperDescriptor", editDeveloperDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the developer with. Each non-empty field value will replace the
     * corresponding field value of the developer.
     */
    public static class EditDeveloperDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<String> projects;
        private Date dateJoined;
        private DeveloperRoles role;
        private Salary salary;
        private GithubId githubId;
        private Rating rating;

        public EditDeveloperDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDeveloperDescriptor(EditDeveloperDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setProjects(toCopy.projects);
            setDateJoined(toCopy.dateJoined);
            setRole(toCopy.role);
            setSalary(toCopy.salary);
            setGithubId(toCopy.githubId);
            setRating(toCopy.rating);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                    name, phone, email, address, projects, dateJoined, role, salary, githubId, rating);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setProjects(Set<String> projects) {
            this.projects = (projects != null) ? new HashSet<>(projects) : null;
        }

        public Optional<Set<String>> getProjects() {
            return (projects != null) ? Optional.of(Collections.unmodifiableSet(projects)) : Optional.empty();
        }

        public void setDateJoined(Date dateJoined) {
            this.dateJoined = dateJoined;
        }

        public Optional<Date> getDateJoined() {
            return Optional.ofNullable(dateJoined);
        }

        public void setRole(DeveloperRoles role) {
            this.role = role;
        }

        public Optional<DeveloperRoles> getRole() {
            return Optional.ofNullable(role);
        }

        public void setSalary(Salary salary) {
            this.salary = salary;
        }

        public Optional<Salary> getSalary() {
            return Optional.ofNullable(salary);
        }

        public void setGithubId(GithubId githubId) {
            this.githubId = githubId;
        }

        public Optional<GithubId> getGithubId() {
            return Optional.ofNullable(githubId);
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditDeveloperDescriptor)) {
                return false;
            }

            EditDeveloperDescriptor otherEditDeveloperDescriptor = (EditDeveloperDescriptor) other;
            return Objects.equals(name, otherEditDeveloperDescriptor.name)
                    && Objects.equals(phone, otherEditDeveloperDescriptor.phone)
                    && Objects.equals(email, otherEditDeveloperDescriptor.email)
                    && Objects.equals(address, otherEditDeveloperDescriptor.address)
                    && Objects.equals(projects, otherEditDeveloperDescriptor.projects)
                    && Objects.equals(dateJoined, otherEditDeveloperDescriptor.dateJoined)
                    && Objects.equals(role, otherEditDeveloperDescriptor.role)
                    && Objects.equals(salary, otherEditDeveloperDescriptor.salary);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("projects", projects)
                    .add("dateJoined", dateJoined)
                    .add("role", role)
                    .add("salary", salary)
                    .add("githubId", githubId)
                    .add("rating", rating)
                    .toString();
        }
    }
}