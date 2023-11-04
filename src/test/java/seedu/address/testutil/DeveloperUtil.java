package seedu.address.testutil;

import seedu.address.logic.commands.add.AddDeveloperCommand;
import seedu.address.logic.commands.edit.EditDeveloperCommand;
import seedu.address.model.developer.Developer;

import java.util.Set;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * A utility class for Developer.
 */
public class DeveloperUtil {

    /**
     * Returns an add command string for adding the {@code developer}.
     */
    public static String getAddCommand(Developer developer) {
        return AddDeveloperCommand.COMMAND_WORD + " " + getPersonDetails(developer);
    }

    /**
     * Returns the part of command string for the given {@code developer}'s details.
     */
    public static String getPersonDetails(Developer developer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + developer.getName().fullName + " ");
        sb.append(PREFIX_PHONE + developer.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + developer.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + developer.getAddress().value + " ");
        developer.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditDeveloperCommand.EditDeveloperDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
