package seedu.address.model.client;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;

/**
 * Tests that a {@code Client}'s {@code Role} matches any of the keywords given.
 */
public class RoleClientContainsKeywordsPredicate implements KeywordPredicate<Client> {
    private final List<String> keywords;

    public RoleClientContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getRole().toString(),
                keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoleClientContainsKeywordsPredicate)) {
            return false;
        }

        RoleClientContainsKeywordsPredicate otherRoleContainsKeywordsPredicate =
                (RoleClientContainsKeywordsPredicate) other;
        return keywords.equals(otherRoleContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
