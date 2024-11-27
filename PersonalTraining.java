package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PersonalTraining {

    public Scene createPersonalTrainingScene(Stage primaryStage) {
        // Create UI elements for personal training session screen
        Label titleLabel = new Label("Available Trainers for Personal Training");
        ListView<String> trainerListView = new ListView<>();
        Button bookSessionButton = new Button("Book Session");
        Button backButton = new Button("Back");

        // Sample trainers (replace with database fetch logic if needed)
        ObservableList<String> trainers = FXCollections.observableArrayList(
                "Trainer A - Specializes in Strength Training",
                "Trainer B - Specializes in Cardio and Endurance",
                "Trainer C - Specializes in Yoga and Flexibility",
                "Trainer D - Specializes in Weight Loss"
        );
        trainerListView.setItems(trainers);

        // Book Session Button Action
        bookSessionButton.setOnAction(_ -> {
            String selectedTrainer = trainerListView.getSelectionModel().getSelectedItem();
            if (selectedTrainer != null) {
                System.out.println("Session booked with: " + selectedTrainer);
                // Add logic to save booking to the database or show confirmation
            } else {
                System.out.println("No trainer selected.");
            }
        });

        // Back Button Action
        backButton.setOnAction(_ -> {
            Scene timetableScene = new Timetable().createTimetableScene(primaryStage);
            primaryStage.setScene(timetableScene);
        });

        // Layout
        VBox vbox = new VBox(10, titleLabel, trainerListView, bookSessionButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        return new Scene(vbox, 400, 400);
    }
}
