package seedu.address.model.developer;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.KeywordPredicate;

/**
 * Tests that a {@code Developer}'s {@code Salary} matches any of the keywords given.
 */
public class SalaryContainsKeywordsPredicate implements KeywordPredicate<Developer> {
    private final List<String> keywords;

    public SalaryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Developer developer) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(developer.getSalary().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SalaryContainsKeywordsPredicate)) {
            return false;
        }

        SalaryContainsKeywordsPredicate otherSalaryContainsKeywordsPredicate = (SalaryContainsKeywordsPredicate) other;
        return keywords.equals(otherSalaryContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
