package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrackWorkout {

    private Members members = new Members(); // Instance of the Members class

    /**
     * Creates the Track Workout scene.
     *
     * @param primaryStage The main stage of the application.
     * @param userName     The logged-in user's username.
     * @return The Scene for tracking workouts.
     */
    public Scene createTrackWorkoutScene(Stage primaryStage, String userName) {
        // Title label
        Label titleLabel = new Label("Log Your Workout Session");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #00796b;");

        // Input fields for workout details
        TextField workoutTypeField = new TextField();
        workoutTypeField.setPromptText("Enter Workout Type (e.g., Cardio, Strength)");

        TextField durationField = new TextField();
        durationField.setPromptText("Enter Duration (in minutes)");

        TextField caloriesBurnedField = new TextField();
        caloriesBurnedField.setPromptText("Enter Calories Burned");

        // Button to log workout
        Button logWorkoutButton = new Button("Log Workout");
        logWorkoutButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Table to display logged workout details
        TableView<WorkoutSession> workoutTable = new TableView<>();

        TableColumn<WorkoutSession, String> typeColumn = new TableColumn<>("Workout Type");
        typeColumn.setCellValueFactory(param -> param.getValue().workoutTypeProperty());
        typeColumn.setMinWidth(150);

        TableColumn<WorkoutSession, Integer> durationColumn = new TableColumn<>("Duration (min)");
        durationColumn.setCellValueFactory(param -> param.getValue().durationProperty().asObject());
        durationColumn.setMinWidth(100);

        TableColumn<WorkoutSession, Integer> caloriesColumn = new TableColumn<>("Calories Burned");
        caloriesColumn.setCellValueFactory(param -> param.getValue().caloriesBurnedProperty().asObject());
        caloriesColumn.setMinWidth(100);

        TableColumn<WorkoutSession, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(param -> param.getValue().dateProperty());
        dateColumn.setMinWidth(150);

        workoutTable.getColumns().add(typeColumn);
        workoutTable.getColumns().add(durationColumn);
        workoutTable.getColumns().add(caloriesColumn);
        workoutTable.getColumns().add(dateColumn);

        // ObservableList to store workout details
        ObservableList<WorkoutSession> workoutHistory = FXCollections.observableArrayList();

        // Button action for logging workout
        logWorkoutButton.setOnAction(_ -> {
            String workoutType = workoutTypeField.getText().trim();
            String durationStr = durationField.getText().trim();
            String caloriesStr = caloriesBurnedField.getText().trim();

            // Debugging logs to check input values
            System.out.println("WorkoutType: " + workoutType);
            System.out.println("Duration: " + durationStr);
            System.out.println("CaloriesBurned: " + caloriesStr);

            // Input validation
            if (workoutType.isEmpty() || durationStr.isEmpty() || caloriesStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Input", "Please fill out all fields to log your workout.");
                return;
            }

            try {
                int duration = Integer.parseInt(durationStr);
                int caloriesBurned = Integer.parseInt(caloriesStr);

                // Log workout in the database
                boolean success = members.logWorkout(userName, workoutType, duration, caloriesBurned);
                if (success) {
                    // Add workout details to the table
                    WorkoutSession newSession = new WorkoutSession(workoutType, duration, caloriesBurned, java.time.LocalDate.now().toString());
                    workoutHistory.add(newSession);

                    // Update table view
                    workoutTable.setItems(workoutHistory);

                    // Debugging log for added session
                    System.out.println("Workout added to history: " + newSession);

                    showAlert(Alert.AlertType.INFORMATION, "Workout Logged", "Your workout has been successfully logged!");

                    // Clear fields after submission
                    workoutTypeField.clear();
                    durationField.clear();
                    caloriesBurnedField.clear();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to log workout. Please try again.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Duration and Calories Burned must be numeric values.");
            }
        });

        // Back button to return to the home scene
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        backButton.setOnAction(_ -> primaryStage.setScene(new LoginApp().createHomeScene(primaryStage)));

        // Layout for the scene
        VBox layout = new VBox(20, titleLabel, workoutTypeField, durationField, caloriesBurnedField, logWorkoutButton, workoutTable, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 700, 600);
    }

    /**
     * Utility method to show an alert box.
     *
     * @param alertType The type of alert to display.
     * @param title     The title of the alert.
     * @param message   The message content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
