package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;

import org.controlsfx.control.Rating;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.developer.Developer;


/**
 * A UI component that displays information of a {@code Developer}.
 */
public class DeveloperCard extends UiPart<Region> {

    private static final String FXML = "DeveloperListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Developer developer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label dateJoined;
    @FXML
    private Label role;
    @FXML
    private Label salary;

    @FXML
    private Label githubId;
    @FXML
    private Rating rating;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Developer} and index to display.
     */
    public DeveloperCard(seedu.address.model.developer.Developer developer, int displayedIndex) {
        super(FXML);
        this.developer = developer;
        id.setText(displayedIndex + ". ");
        name.setText(developer.getName().fullName);
        phone.setText("Contact: " + developer.getPhone().value);
        email.setText("Email: " + developer.getEmail().value);
        role.setText("Role: " + developer.getRole().role);
        githubId.setText("GitHub ID: " + developer.getGithubId().username);
        rating.setPartialRating(true);
        rating.setUpdateOnHover(false);
        rating.setOnMousePressed(null);
        rating.setOnMouseEntered(null);
        //rating.setDisable(true);
        rating.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        rating.setMax(5);
        rating.autosize();
        rating.setDisable(true);
        rating.setRating(developer.getRating().rating);
        address.setText("Address: " + developer.getAddress().value);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date joined = developer.getDateJoined().value;
        // Calculate the period between the two dates
        Period period = Period.between(joined.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        // Extract years and months from the period
        int years = period.getYears();
        int months = period.getMonths();
        dateJoined.setText("Date Joined: " + dateFormat.format(joined) + "\n(" + years + " years " + months + " months)");
        salary.setText("Salary: $" + developer.getSalary().salary);
        developer.getProjects().stream()
                .sorted(Comparator.comparing(tag -> tag))
                .forEach(tag -> tags.getChildren().add(new Label(tag)));
    }
}
