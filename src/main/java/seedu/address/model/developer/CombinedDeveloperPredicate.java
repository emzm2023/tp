package seedu.address.model.developer;

import java.util.function.Predicate;

public class CombinedDeveloperPredicate implements Predicate<Developer> {
    private final Predicate<Developer> namePredicate;
    private final Predicate<Developer> rolePredicate;
    private final Predicate<Developer> addressPredicate;
    private final Predicate<Developer> dateJoinedPredicate;
    private final Predicate<Developer> emailPredicate;
    private final Predicate<Developer> phonePredicate;
    private final Predicate<Developer> projectPredicate;
    private final Predicate<Developer> salaryPredicate;
    private final Predicate<Developer> ratingPredicate;
    private final Predicate<Developer> githubIdPredicate;

    public CombinedDeveloperPredicate(Predicate<Developer> namePredicate,
                                      Predicate<Developer> rolePredicate,
                                      Predicate<Developer> addressPredicate,
                                      Predicate<Developer> dateJoinedPredicate,
                                      Predicate<Developer> emailPredicate,
                                      Predicate<Developer> phonePredicate,
                                      Predicate<Developer> projectPredicate,
                                      Predicate<Developer> salaryPredicate,
                                      Predicate<Developer> ratingPredicate,
                                      Predicate<Developer> githubIdPredicate) {
        this.namePredicate = namePredicate;
        this.rolePredicate = rolePredicate;
        this.addressPredicate = addressPredicate;
        this.dateJoinedPredicate = dateJoinedPredicate;
        this.emailPredicate = emailPredicate;
        this.phonePredicate = phonePredicate;
        this.projectPredicate = projectPredicate;
        this.salaryPredicate = salaryPredicate;
        this.ratingPredicate = ratingPredicate;
        this.githubIdPredicate = githubIdPredicate;
    }

    @Override
    public boolean test(Developer developer) {
        return namePredicate.test(developer) &&
                rolePredicate.test(developer) &&
                addressPredicate.test(developer) &&
                dateJoinedPredicate.test(developer) &&
                emailPredicate.test(developer) &&
                phonePredicate.test(developer) &&
                projectPredicate.test(developer) &&
                salaryPredicate.test(developer) &&
                ratingPredicate.test(developer) &&
                githubIdPredicate.test(developer);
    }
}
