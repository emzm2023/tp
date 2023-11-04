package seedu.address.model.developer;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;

/**
 * Tests that a {@code Developer}'s {@code Rating} matches any of the keywords given.
 */
public class RatingContainsKeywordsPredicate implements KeywordPredicate<Developer> {
    private final List<String> keywords;

    public RatingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Developer developer) {
        // Assuming Developer has a getRating() method that returns a Rating object
        // and the Rating object has a toString() method that returns its string representation.
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(developer.getRating().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RatingContainsKeywordsPredicate)) {
            return false;
        }

        RatingContainsKeywordsPredicate otherRatingContainsKeywordsPredicate = (RatingContainsKeywordsPredicate) other;
        return keywords.equals(otherRatingContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
