package seedu.address.ui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;

import java.util.stream.Collectors;


/**
 * A UI component that displays information of a {@code Project}.
 */
public class ProjectCard extends UiPart<Region> {

    private static final String FXML = "ProjectListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Project project;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private TableView<Deadline> table;
//    private TableView<Data> table;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ProjectCard (Project project, int displayedIndex) {
        super(FXML);
        this.project = project;
        id.setText(displayedIndex + ". ");
        name.setText(project.getName());
        description.setText(project.getProjectDescription().desc);
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setMinWidth(250);
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description"));

        TableColumn priorityCol = new TableColumn("Priority");
        priorityCol.setMinWidth(100);
        priorityCol.setCellValueFactory(
                new PropertyValueFactory<>("priority"));
        TableColumn doneCol = new TableColumn("Done");
        doneCol.setMinWidth(100);
        doneCol.setCellValueFactory(
                new PropertyValueFactory<>("isDone"));
        table.getColumns().addAll(doneCol, priorityCol,dateCol,descriptionCol);
//        ObservableList<Data> data = FXCollections.observableList(project.getProjectDeadlines().stream()
//                .map(deadline ->new Data(deadline)).collect(Collectors.toList()));
//        table.setItems(data);
        table.setItems(project.getProjectDeadlinesList());
    }
    
    public static class Data {
        private final SimpleStringProperty date;
        private final SimpleStringProperty description;
        private final SimpleStringProperty priority;

        private SimpleBooleanProperty isDone;
        private Data(Deadline deadline){
            date = new SimpleStringProperty(deadline.getObjDate().toString());
            description = new SimpleStringProperty(deadline.getDescription().desc);
            priority = new SimpleStringProperty(deadline.getObjPriority().toString());
            isDone = new SimpleBooleanProperty(deadline.getIsDone());

        }
        public String getDate() {
            return date.get();
        }


        public void setDate(String date) {
            this.date.set(date);
        }
        public boolean getIsDone() {
            return isDone.get();
        }


        public void setIsDone(boolean isDone) {
            this.isDone.set(isDone);
        }

        public String getDescription() {
            return description.get();
        }


        public void setDescription(String description) {
            this.description.set(description);
        }

        public String getPriority() {
            return priority.get();
        }


        public void setPriority(String priority) {
            this.priority.set(priority);
        }

    }
}