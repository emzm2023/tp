package seedu.address.model.developer;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;


/**
 * Tests that a {@code Developer}'s {@code Role} matches any of the keywords given.
 */
public class RoleDeveloperContainsKeywordsPredicate implements KeywordPredicate<Developer> {
    private final List<String> keywords;

    public RoleDeveloperContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Developer developer) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(developer.getRole().toString()
                , keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoleDeveloperContainsKeywordsPredicate)) {
            return false;
        }

        RoleDeveloperContainsKeywordsPredicate otherRoleContainsKeywordsPredicate =
                (RoleDeveloperContainsKeywordsPredicate) other;
        return keywords.equals(otherRoleContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
