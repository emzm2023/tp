package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Name;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Description;
import seedu.address.model.project.Project;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String projectName;
    private final String description;
    private final List<String> deadlines = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProject} with the given {@code projectName}.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("projectName") String projectName,
                              @JsonProperty("description") String description,
                              @JsonProperty("deadlines") List<String> deadlines) {

        this.projectName = projectName;
        this.description = description;
        this.deadlines.clear();
        if (deadlines != null) {
            this.deadlines.addAll(deadlines);
        }
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {

        projectName = source.getProjectName().fullName;
        description = source.getProjectDescription().desc;
        deadlines.addAll(source.getProjectDeadlines().stream()
                .map(Deadline::getStringRepresentation).collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Project toModelType() throws IllegalValueException {
        if (projectName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(projectName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(projectName);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (deadlines == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }

        // Parse and validate the set of deadlines
        final List<Deadline> modelDeadlines = new ArrayList<>();
        for (String deadline : deadlines) {
            if (!Deadline.isValidDeadline(deadline)) {
                throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
            }
            modelDeadlines.add(new Deadline(deadline, modelDeadlines.size() + 1));
        }

        return new Project(modelName, modelDescription, modelDeadlines);
    }

}
