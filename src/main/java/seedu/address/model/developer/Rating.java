package seedu.address.model.developer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Developer's rating in the company.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {
    public static final String MESSAGE_CONSTRAINTS = "Rating should be between 0.0 and 5.0";
    public static final String VALIDATION_REGEX = "[0-5](\\.[0-9]*)?";
    public final double rating;

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_CONSTRAINTS);
        this.rating = Double.parseDouble(rating);
    }

    /**
     * Returns true if a given string is a valid salary.
     */
    public static boolean isValidRating(String test) {
        return test.matches(VALIDATION_REGEX) && Double.valueOf(test) <= 5 && Double.valueOf(test) >= 0;
    }

    @Override
    public String toString() {
        return rating + "";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rating)) {
            return false;
        }

        Rating otherRating = (Rating) other;
        return rating == otherRating.rating;
    }

    @Override
    public int hashCode() {
        return new Double(rating).hashCode();
    }
}
