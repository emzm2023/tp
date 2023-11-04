package seedu.address.model.developer;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;


/**
 * Tests that a {@code Developer}'s {@code Name} matches any of the keywords given.
 */
public class NameDeveloperContainsKeywordsPredicate implements KeywordPredicate<Developer> {
    private final List<String> keywords;

    public NameDeveloperContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Developer developer) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(developer.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameDeveloperContainsKeywordsPredicate)) {
            return false;
        }

        NameDeveloperContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameDeveloperContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
