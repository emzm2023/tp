package seedu.address.model.client;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;

/**
 * Tests that a {@code Client}'s associated {@code Project} names match any of the keywords given.
 */
public class ProjectClientContainsKeywordsPredicate implements KeywordPredicate<Client> {
    private final List<String> keywords;

    public ProjectClientContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getProjects().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.client.ProjectClientContainsKeywordsPredicate)) {
            return false;
        }

        seedu.address.model.client.ProjectClientContainsKeywordsPredicate otherPredicate =
                (seedu.address.model.client.ProjectClientContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
