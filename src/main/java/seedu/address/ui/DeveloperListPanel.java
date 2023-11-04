package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.developer.Developer;

/**
 * Panel containing the list of persons.
 */
public class DeveloperListPanel extends UiPart<Region> {
    private static final String FXML = "DeveloperListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeveloperListPanel.class);

    @FXML
    private ListView<Developer> developerListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public DeveloperListPanel(ObservableList<Developer> developerList) {
        super(FXML);
        developerListView.setItems(developerList);
        developerListView.setCellFactory(listView -> new DeveloperListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code DeveloperCard}.
     */
    class DeveloperListViewCell extends ListCell<Developer> {
        @Override
        protected void updateItem(Developer person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeveloperCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
