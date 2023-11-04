package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCUMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

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
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientRoles;
import seedu.address.model.client.Document;
import seedu.address.model.commons.Name;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

/**
 * Edits the details of an existing client in the address book.
 */
public class EditClientCommand extends Command {

    public static final String COMMAND_WORD = "edit-client";
    public static final String MESSAGE_EDIT_CLIENT_SUCCESS = "Edited Client: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CLIENT =
            "The details of the client in the address book are already " + "as given.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the client identified "
            + "by the index number used in the displayed client list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) " + "[" + PREFIX_NAME + "NAME] " + "[" + PREFIX_PHONE
            + "PHONE] " + "[" + PREFIX_EMAIL + "EMAIL] " + "[" + PREFIX_ADDRESS + "ADDRESS] " + "[" + PREFIX_ROLE
            + "ROLE] " + "[" + PREFIX_PROJECT + "PROJECT]...\n" + "[" + PREFIX_ORGANISATION + "ORGANISATION] " + "["
            + PREFIX_DOCUMENT + "DOCUMENT] " + "Example: \n" + COMMAND_WORD + " 1 " + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final Index index;

    private final EditClientDescriptor editClientDescriptor;


    /**
     * @param index                of the client in the filtered client list to edit
     * @param editClientDescriptor details to edit the client with
     */
    public EditClientCommand(Index index, EditClientDescriptor editClientDescriptor) {
        requireNonNull(index);
        requireNonNull(editClientDescriptor);

        this.index = index;
        this.editClientDescriptor = new EditClientDescriptor(editClientDescriptor);
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code clientToEdit}
     * edited with {@code editClientDescriptor}.
     */
    static Client createEditedClient(Client clientToEdit, EditClientDescriptor editClientDescriptor) {
        assert clientToEdit != null;

        Name updatedName = editClientDescriptor.getName().orElse(clientToEdit.getName());
        Phone updatedPhone = editClientDescriptor.getPhone().orElse(clientToEdit.getPhone());
        Email updatedEmail = editClientDescriptor.getEmail().orElse(clientToEdit.getEmail());
        Address updatedAddress = editClientDescriptor.getAddress().orElse(clientToEdit.getAddress());
        ClientRoles updatedRole = editClientDescriptor.getRole().orElse(clientToEdit.getRole());
        Set<String> updatedProjects = editClientDescriptor.getProjects().orElse(clientToEdit.getProjects());
        Name updatedOrganisation = editClientDescriptor.getOrganisation().orElse(clientToEdit.getOrganisation());
        Document updatedDocument = editClientDescriptor.getDocument().orElse(clientToEdit.getDocument());

        return new Client(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRole, updatedProjects,
                updatedOrganisation, updatedDocument);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        Client editedClient = createEditedClient(clientToEdit, editClientDescriptor);

        if (!clientToEdit.isSamePerson(editedClient) && model.hasClient(editedClient)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }
        String res = model.areProjectsValid(editedClient);
        if (res != null) {
            throw new CommandException(String.format(Messages.MESSAGE_NONEXISTENT_PROJECT, res));
        }

        String successMessage = String.format(MESSAGE_EDIT_CLIENT_SUCCESS, Messages.format(editedClient));
        TabIndex index = TabIndex.Client;

        model.setClient(clientToEdit, editedClient);
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);
        model.commitAddressBook(model, successMessage, index);
        return new CommandResult(successMessage, index);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditClientCommand)) {
            return false;
        }

        EditClientCommand otherEditClientCommand = (EditClientCommand) other;
        return index.equals(otherEditClientCommand.index)
                && editClientDescriptor.equals(otherEditClientCommand.editClientDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("index", index).add("editClientDescriptor", editClientDescriptor).toString();
    }

    /**
     * Stores the details to edit the client with. Each non-empty field value will replace the
     * corresponding field value of the client.
     */
    public static class EditClientDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<String> projects;
        private ClientRoles role;
        private Name organisation;
        private Document document;

        public EditClientDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditClientDescriptor(EditClientDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setProjects(toCopy.projects);
            setRole(toCopy.role);
            setOrganisation(toCopy.organisation);
            setDocument(toCopy.document);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, projects, role, organisation, document);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Set<String>> getProjects() {
            return (projects != null) ? Optional.of(Collections.unmodifiableSet(projects)) : Optional.empty();
        }

        public void setProjects(Set<String> projects) {
            this.projects = (projects != null) ? new HashSet<>(projects) : null;
        }

        public Optional<Name> getOrganisation() {
            return Optional.ofNullable(organisation);
        }

        public void setOrganisation(Name organisation) {
            this.organisation = organisation;
        }

        public Optional<Document> getDocument() {
            return Optional.ofNullable(document);
        }

        public void setDocument(Document document) {
            this.document = document;
        }

        public Optional<ClientRoles> getRole() {
            return Optional.ofNullable(role);
        }

        public void setRole(ClientRoles role) {
            this.role = role;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditClientDescriptor)) {
                return false;
            }

            EditClientDescriptor otherEditClientDescriptor = (EditClientDescriptor) other;
            return Objects.equals(name, otherEditClientDescriptor.name)
                    && Objects.equals(phone, otherEditClientDescriptor.phone)
                    && Objects.equals(email, otherEditClientDescriptor.email)
                    && Objects.equals(address, otherEditClientDescriptor.address)
                    && Objects.equals(projects, otherEditClientDescriptor.projects)
                    && Objects.equals(role, otherEditClientDescriptor.role)
                    && Objects.equals(organisation, otherEditClientDescriptor.organisation)
                    && Objects.equals(document, otherEditClientDescriptor.document);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).add("name", name).add("phone", phone).add("email", email).add("address",
                    address).add("projects", projects).add("role", role).add("organisation", organisation).add(
                            "document", document).toString();
        }
    }
}
