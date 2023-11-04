package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TabIndex;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.Name;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Description;
import seedu.address.model.project.Project;

/**
 * Edits the details of an existing project in the address book.
 */
public class EditProjectCommand extends Command {

    public static final String COMMAND_WORD = "edit-project";
    public static final String MESSAGE_EDIT_PROJECT_SUCCESS = "Edited Project: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROJECT = "The details of the project in the address book are already as given.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the project identified "
            + "by the index number used in the displayed project list. "
            + "Existing values will be overwritten by the input values.\n"
            + Deadline.MESSAGE_CONSTRAINTS + "\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_DEADLINE + "DEADLINE_DATE,DEADLINE_DESCRIPTION,PRIORITY,IS_DONE]...\n"
            + "Example: \n" + COMMAND_WORD + " 1 "
            + PREFIX_DEADLINE + "31-12-2019,Develop front end interface,HIGH,0 "
            + PREFIX_DEADLINE + "01-02-2020,Develop back end,HIGH,0";

    private final Index index;

    private final EditProjectDescriptor editProjectDescriptor;


    /**
     * @param index                 of the project in the filtered project list to edit
     * @param editProjectDescriptor details to edit the project with
     */
    public EditProjectCommand(Index index, EditProjectDescriptor editProjectDescriptor) {
        requireNonNull(index);
        requireNonNull(editProjectDescriptor);

        this.index = index;
        this.editProjectDescriptor = new EditProjectDescriptor(editProjectDescriptor);
    }

    /**
     * Creates and returns a {@code Project} with the details of {@code projectToEdit}
     * edited with {@code editProjectDescriptor}.
     */
    static seedu.address.model.project.Project createEditedProject(seedu.address.model.project.Project projectToEdit, EditProjectDescriptor editProjectDescriptor) {
        assert projectToEdit != null;

        Name name = projectToEdit.getProjectName();
        Description updatedDescription = editProjectDescriptor.getDescription()
                .orElse(projectToEdit.getProjectDescription());
        List<Deadline> updatedDeadlines = editProjectDescriptor.getDeadlines().orElse(projectToEdit.getProjectDeadlines());

        return new seedu.address.model.project.Project(name, updatedDescription, updatedDeadlines);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(index.getZeroBased());
        Project editedProject = createEditedProject(projectToEdit, editProjectDescriptor);

        if (!projectToEdit.isSameProject(editedProject) && model.hasProject(editedProject)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        String successMessage = String.format(MESSAGE_EDIT_PROJECT_SUCCESS, Messages.format(editedProject));
        TabIndex index = TabIndex.developers;

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        model.commitAddressBook(model, successMessage, index);
        return new CommandResult(successMessage, index);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditProjectCommand)) {
            return false;
        }

        EditProjectCommand otherEditProjectCommand = (EditProjectCommand) other;
        return index.equals(otherEditProjectCommand.index)
                && editProjectDescriptor.equals(otherEditProjectCommand.editProjectDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editProjectDescriptor", editProjectDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the project with. Each non-empty field value will replace the
     * corresponding field value of the project.
     */
    public static class EditProjectDescriptor {
        private Description desc;
        private List<Deadline> deadlines;

        public EditProjectDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditProjectDescriptor(EditProjectDescriptor toCopy) {
            setDescription(toCopy.desc);
            setDeadlines(toCopy.deadlines);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                    desc, deadlines);
        }

        public void setDescription(Description desc) {
            this.desc = desc;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(desc);
        }

        public void setDeadlines(List<Deadline> deadlines) {
            this.deadlines = (deadlines != null) ? new ArrayList<>(deadlines) : null;
        }

        public Optional<List<Deadline>> getDeadlines() {
            return (deadlines != null) ? Optional.of(Collections.unmodifiableList(deadlines)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditProjectDescriptor)) {
                return false;
            }

            EditProjectDescriptor otherEditProjectDescriptor = (EditProjectDescriptor) other;
            return Objects.equals(desc, otherEditProjectDescriptor.desc)
                    && Objects.equals(deadlines, otherEditProjectDescriptor.deadlines);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("description", desc)
                    .add("deadlines", deadlines)
                    .toString();
        }
    }
}