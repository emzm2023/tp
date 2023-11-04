package seedu.address.model.developer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DeveloperNotFoundException;
import seedu.address.model.person.exceptions.DuplicateDeveloperException;

/**
 * A list of developers that enforces uniqueness between its elements and does not allow nulls.
 * A developer is considered unique by comparing using {@code Developer#isSameDeveloper(Developer)}.
 * As such, adding and updating of developers use Developer#isSameDeveloper(Developer) for equality to ensure that
 * the developer being added or updated is unique in terms of identity in the UniqueDeveloperList.
 * However, the removal of a developer uses Developer#equals(Object) to ensure that the developer with exactly the
 * same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Developer#isSameDeveloper(Developer)
 */
public class UniqueDeveloperList implements Iterable<Developer> {

    private final ObservableList<Developer> internalList = FXCollections.observableArrayList();
    private final ObservableList<Developer> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent developer as the given argument.
     */
    public boolean contains(Developer toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDeveloper);
    }

    /**
     * Adds a developer to the list.
     * The developer must not already exist in the list.
     */
    public void add(Developer toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDeveloperException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the developer {@code target} in the list with {@code editedDeveloper}.
     * {@code target} must exist in the list.
     * The developer identity of {@code editedDeveloper} must not be the same as another existing developer in the list.
     */
    public void setDeveloper(Developer target, Developer editedDeveloper) {
        requireAllNonNull(target, editedDeveloper);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DeveloperNotFoundException();
        }

        if (!target.isSameDeveloper(editedDeveloper) && contains(editedDeveloper)) {
            throw new DuplicateDeveloperException();
        }

        internalList.set(index, editedDeveloper);
    }

    /**
     * Removes the equivalent developer from the list.
     * The developer must exist in the list.
     */
    public void remove(Developer toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DeveloperNotFoundException();
        }
    }

    public void setDevelopers(UniqueDeveloperList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code developers}.
     * {@code developers} must not contain duplicate developers.
     */
    public void setDevelopers(List<Developer> developers) {
        requireAllNonNull(developers);
        if (!developersAreUnique(developers)) {
            throw new DuplicateDeveloperException();
        }

        internalList.setAll(developers);
    }

    public void updateDeveloperProjects(String project) {
        iterator().forEachRemaining(
                developer -> {
                    Set<String> newprojectset = new HashSet<>(developer.getProjects());
                    newprojectset.remove(project);
                    setDeveloper(developer, new Developer(developer.getName(), developer.getPhone(),
                            developer.getEmail(),
                            developer.getAddress(), developer.getRole(), newprojectset,
                            developer.getSalary(), developer.getDateJoined(), developer.getGithubId(),
                            developer.getRating()));
                });
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Developer> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Developer> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueDeveloperList)) {
            return false;
        }

        UniqueDeveloperList otherUniqueDeveloperList = (UniqueDeveloperList) other;
        return internalList.equals(otherUniqueDeveloperList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code developers} contains only unique developers.
     */
    private boolean developersAreUnique(List<Developer> developers) {
        for (int i = 0; i < developers.size() - 1; i++) {
            for (int j = i + 1; j < developers.size(); j++) {
                if (developers.get(i).isSameDeveloper(developers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
