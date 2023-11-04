package seedu.address.model.developer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a GitHub username with specific constraints.
 * Guarantees: immutable; is valid as declared in {@link #isValidGithubId(String)}
 */
public class GithubId {

    public static final String MESSAGE_CONSTRAINTS =
            "GitHub usernames should only contain alphanumeric characters or hyphens, and should not violate length constraints";

    public static final String VALIDATION_REGEX = "^(?!-)[a-zA-Z0-9-]{1,38}[a-zA-Z0-9]$";

    public final String username;

    /**
     * Constructs a {@code GithubId}.
     *
     * @param username A valid GitHub username.
     */
    public GithubId(String username) {
        requireNonNull(username);
        checkArgument(isValidGithubId(username), MESSAGE_CONSTRAINTS);
        this.username = username;
    }

    /**
     * Returns true if a given string is a valid GitHub username.
     */
    public static boolean isValidGithubId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GithubId)) {
            return false;
        }

        GithubId otherId = (GithubId) other;
        return username.equals(otherId.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
