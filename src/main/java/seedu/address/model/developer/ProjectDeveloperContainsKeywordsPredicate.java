package seedu.address.model.developer;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;

/**
 * Tests that a {@code Developer}'s associated {@code Project} names match any of the keywords given.
 */
public class ProjectDeveloperContainsKeywordsPredicate implements KeywordPredicate<Developer> {
    private final List<String> keywords;

    public ProjectDeveloperContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Developer developer) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(developer.getProjects().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectDeveloperContainsKeywordsPredicate)) {
            return false;
        }

        ProjectDeveloperContainsKeywordsPredicate otherPredicate = (ProjectDeveloperContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
