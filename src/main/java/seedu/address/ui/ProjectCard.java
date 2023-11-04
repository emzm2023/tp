package seedu.address.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.model.commons.Date;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Priority;
import seedu.address.model.project.Project;


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
    private ProgressBar progress;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private TableView<Data> table;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ProjectCard(Project project, int displayedIndex) {
        super(FXML);
        this.project = project;
        id.setText(displayedIndex + ". ");
        name.setText(project.getName());
        description.setText(project.getProjectDescription().desc);
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(95);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setMinWidth(260);
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description"));

        TableColumn priorityCol = new TableColumn("Priority");
        priorityCol.setMinWidth(95);
        priorityCol.setCellValueFactory(
                new PropertyValueFactory<>("priority"));
        priorityCol.setCellFactory(column -> new TableCell<ProjectCard, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    // For empty cells, set no text and no background color
                    setText("");
                    setStyle(""); // Clear any previous styling
                } else {
                    setText(item); // Set the text

                    // Customize cell color based on priority
                    if ("HIGH".equals(item)) {
                        // Set the cell background color for HIGH priority
                        setStyle("-fx-background-color: #FFC0CB;");
                    } else if ("MEDIUM".equals(item)) {
                        // Set the cell background color for MEDIUM priority
                        setStyle("-fx-background-color: #FFFFE0;");
                    } else if ("LOW".equals(item)) {
                        // Set the cell background color for LOW priority
                        setStyle("-fx-background-color: #90EE90;");
                    } else {
                        // Handle any other cases or priority values
                        setStyle(""); // Clear any previous styling
                    }
                }
            }
        });
        TableColumn doneCol = new TableColumn("Done");
        doneCol.setMinWidth(95);
        Image imageTrue = new Image(MainApp.class.getResourceAsStream("/images/tick.png"));
        Image imageFalse = new Image(MainApp.class.getResourceAsStream("/images/cross.png"));
        doneCol.setCellFactory(col -> new TableCell<ProjectCard, Boolean>() {

            private final ImageView imageView = new ImageView();

            {
                // initialize ImageView + set as graphic
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                setGraphic(imageView);
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                if (empty || item == null) {
                    // no image for empty cells
                    imageView.setImage(null);
                } else {
                    // set image for non-empty cell
                    imageView.setImage(item ? imageTrue : imageFalse);
                }
            }

        });
        doneCol.setCellValueFactory(
                new PropertyValueFactory<>("isDone"));
        TableColumn indexCol = new TableColumn("#");
        indexCol.setMinWidth(20);
        indexCol.setCellValueFactory(
                new PropertyValueFactory<>("index"));
        table.getColumns().addAll(indexCol, doneCol, priorityCol, dateCol, descriptionCol);
        ObservableList<Data> data = FXCollections.observableList(project.getProjectFilteredDeadlines().stream()
                .map(deadline -> new Data(deadline)).collect(Collectors.toList()));
        project.getProjectFilteredDeadlines().addListener(new ListChangeListener<Deadline>() {
            @Override
            public void onChanged(Change<? extends Deadline> c) {
                ObservableList<Data> data = FXCollections.observableList(project.getProjectFilteredDeadlines().stream()
                        .map(deadline -> new Data(deadline)).collect(Collectors.toList()));
                progress.setProgress(data.stream().filter(t -> t.getIsDone()).count() * 1.0 / data.size());
                table.setItems(data);
            }
        });

        progress.setProgress(data.stream().filter(t -> t.getIsDone()).count() * 1.0 / data.size());
        table.setItems(data);
    }

    public static class Data {
        private final SimpleObjectProperty<Date> date;
        private final SimpleStringProperty description;
        private final SimpleObjectProperty<Priority> priority;

        private final SimpleBooleanProperty isDone;
        private final SimpleIntegerProperty index;
        private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        private Data(Deadline deadline) {
            date = new SimpleObjectProperty(deadline.getDate());
            description = new SimpleStringProperty(deadline.getDescription().desc);
            priority = new SimpleObjectProperty(deadline.getPriority());
            isDone = new SimpleBooleanProperty(deadline.getIsDone());
            index = new SimpleIntegerProperty(deadline.getNum());
        }

        public int getIndex() {
            return index.get();
        }

        public void setIndex(int num) {
            this.index.set(num);
        }

        public String getDate() {
            return date.get().toString();
        }

        public void setDate(String date) throws ParseException {
            this.date.set(new seedu.address.model.commons.Date(date));
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
            return priority.get().toString();
        }

        public void setPriority(String priority) {
            this.priority.set(Priority.valueOf(priority));
        }

    }
}
