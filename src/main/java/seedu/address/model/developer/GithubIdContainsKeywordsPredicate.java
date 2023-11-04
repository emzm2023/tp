package seedu.address.model.developer;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;

/**
 * Tests that a {@code Developer}'s {@code GithubId} matches any of the keywords given.
 */
public class GithubIdContainsKeywordsPredicate implements KeywordPredicate<Developer> {
    private final List<String> keywords;

    public GithubIdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Developer developer) {
        // Assuming Developer has a getGithubId() method that returns the Github ID as a String.
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(developer.getGithubId().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GithubIdContainsKeywordsPredicate)) {
            return false;
        }

        GithubIdContainsKeywordsPredicate otherGithubIdContainsKeywordsPredicate =
                (GithubIdContainsKeywordsPredicate) other;
        return keywords.equals(otherGithubIdContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
