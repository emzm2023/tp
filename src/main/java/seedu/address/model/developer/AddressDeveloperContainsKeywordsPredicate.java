package seedu.address.model.developer;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;


/**
 * Tests that a {@code Developer}'s {@code Address} matches any of the keywords given.
 */
public class AddressDeveloperContainsKeywordsPredicate implements KeywordPredicate<Developer> {
    private final List<String> keywords;

    public AddressDeveloperContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Developer developer) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(developer.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressDeveloperContainsKeywordsPredicate)) {
            return false;
        }

        AddressDeveloperContainsKeywordsPredicate otherPredicate = (AddressDeveloperContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
