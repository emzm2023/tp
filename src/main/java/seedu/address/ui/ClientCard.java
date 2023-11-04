package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;


/**
 * A UI component that displays information of a {@code Client}.
 */
public class ClientCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Client client;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label role;
    @FXML
    private Label address;
    @FXML
    private Label organisation;
    @FXML
    private Label document;


    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ClientCard(Client client, int displayedIndex) {
        super(FXML);
        this.client = client;
        id.setText(displayedIndex + ". ");
        name.setText(client.getName().fullName);
        phone.setText("Contact: " + client.getPhone().value);
        email.setText("Email: " + client.getEmail().value);
        role.setText("Role: " + client.getRole().role);
        organisation.setText("Company: " + client.getOrganisation().fullName);
        document.setText("Document: " + client.getDocument().toString());

        address.setText("Address: " + client.getAddress().value);
        client.getProjects().stream()
                .forEach(tag -> tags.getChildren().add(new Label(tag)));
    }
}
