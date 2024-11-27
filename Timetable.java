package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Timetable {

    public Scene createTimetableScene(Stage primaryStage) {
        // Create TableView for the timetable
        TableView<TimetableEntry> timetableTable = new TableView<>();

        // Define columns for the table
        TableColumn<TimetableEntry, String> dayColumn = new TableColumn<>("Day");
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        dayColumn.setMinWidth(100);

        TableColumn<TimetableEntry, String> activityColumn = new TableColumn<>("Activity");
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        activityColumn.setMinWidth(200);

        TableColumn<TimetableEntry, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setMinWidth(100);

        // Add columns to the TableView
        timetableTable.getColumns().add(dayColumn);
        timetableTable.getColumns().add(activityColumn);
        timetableTable.getColumns().add(timeColumn);


        // Add data to the table
        ObservableList<TimetableEntry> timetableData = FXCollections.observableArrayList(
                new TimetableEntry("Monday", "Yoga", "8:00 AM"),
                new TimetableEntry("Monday", "Zumba", "5:00 PM"),
                new TimetableEntry("Tuesday", "Cardio", "7:00 AM"),
                new TimetableEntry("Tuesday", "Strength Training", "6:00 PM"),
                new TimetableEntry("Wednesday", "Pilates", "8:00 AM"),
                new TimetableEntry("Wednesday", "HIIT", "5:00 PM"),
                new TimetableEntry("Thursday", "Aerobics", "7:00 AM"),
                new TimetableEntry("Thursday", "Boxing", "6:00 PM"),
                new TimetableEntry("Friday", "Spin Class", "8:00 AM"),
                new TimetableEntry("Friday", "Dance", "5:00 PM"),
                new TimetableEntry("Saturday", "Open Gym", "All Day"),
                new TimetableEntry("Sunday", "Rest Day", "")
        );
        timetableTable.setItems(timetableData);

        // Personal Training Session Button
        Button personalTrainingButton = new Button("Personal Training Session");
        personalTrainingButton.setOnAction(_ -> {
            Scene personalTrainingScene = new PersonalTraining().createPersonalTrainingScene(primaryStage);
            primaryStage.setScene(personalTrainingScene);
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(_ -> {
            Scene homeScene = new LoginApp().createHomeScene(primaryStage);
            primaryStage.setScene(homeScene);
        });

        // Layout
        VBox vbox = new VBox(10, timetableTable, personalTrainingButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        return new Scene(vbox, 500, 400); // Adjust scene size to fit the table
    }

    // TimetableEntry class to represent rows in the timetable
    public static class TimetableEntry {
        private String day;
        private String activity;
        private String time;

        public TimetableEntry(String day, String activity, String time) {
            this.day = day;
            this.activity = activity;
            this.time = time;
        }

        public String getDay() {
            return day;
        }

        public String getActivity() {
            return activity;
        }

        public String getTime() {
            return time;
        }
    }
}
