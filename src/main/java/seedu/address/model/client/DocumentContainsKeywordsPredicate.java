package seedu.address.model.client;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;

/**
 * Tests that a {@code Developer}'s {@code Document} matches any of the keywords given.
 */
public class DocumentContainsKeywordsPredicate implements KeywordPredicate<Client> {
    private final List<String> keywords;

    public DocumentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getDocument().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DocumentContainsKeywordsPredicate)) {
            return false;
        }

        DocumentContainsKeywordsPredicate otherDocumentContainsKeywordsPredicate = (DocumentContainsKeywordsPredicate) other;
        return keywords.equals(otherDocumentContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
