package seedu.address.model.client;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;

/**
 * Tests that a {@code Client}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneClientContainsKeywordsPredicate implements KeywordPredicate<Client> {
    private final List<String> keywords;

    public PhoneClientContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getPhone().value,
                keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.client.PhoneClientContainsKeywordsPredicate)) {
            return false;
        }

        seedu.address.model.client.PhoneClientContainsKeywordsPredicate otherPredicate =
                (seedu.address.model.client.PhoneClientContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
