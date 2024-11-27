package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;


import java.util.HashMap;
import java.util.Map;

public class ChallengesAndFeedback {

    /**
     * Creates the Challenges and Feedback scene.
     *
     * @param primaryStage The main stage of the application.
     * @return The Scene for managing challenges and feedback.
     */
    public Scene createChallengesAndFeedbackScene(Stage primaryStage) {
        // Title label
        Label titleLabel = new Label("Join Challenges and Provide Feedback");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #00796b;");

        // Table for Challenges
        TableView<Map<String, String>> challengesTable = new TableView<>();
        ObservableList<Map<String, String>> challengesData = FXCollections.observableArrayList();

        // Define columns
        TableColumn<Map<String, String>, String> challengeNameColumn = new TableColumn<>("Challenge Name");
        challengeNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get("Challenge Name")));
        TableColumn<Map<String, String>, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get("Description")));

        challengesTable.getColumns().add(challengeNameColumn);
        challengesTable.getColumns().add(descriptionColumn);

        challengesTable.setItems(challengesData);

        // Mock data for challenges
        Map<String, String> challenge1 = new HashMap<>();
        challenge1.put("Challenge Name", "10K Steps Daily");
        challenge1.put("Description", "Walk 10,000 steps every day for a week.");
        challengesData.add(challenge1);

        Map<String, String> challenge2 = new HashMap<>();
        challenge2.put("Challenge Name", "Lose 5 Pounds");
        challenge2.put("Description", "Lose 5 pounds in a month with healthy habits.");
        challengesData.add(challenge2);

        // Button to Join Challenge
        Button joinChallengeButton = new Button("Join Challenge");
        joinChallengeButton.setOnAction(_ -> {
            Map<String, String> selectedChallenge = challengesTable.getSelectionModel().getSelectedItem();
            if (selectedChallenge == null) {
                showAlert(Alert.AlertType.WARNING, "No Challenge Selected", "Please select a challenge to join.");
            } else {
                String challengeName = selectedChallenge.get("Challenge Name");
                showAlert(Alert.AlertType.INFORMATION, "Challenge Joined", "You have joined the challenge: " + challengeName);

                // Store joined challenge in the database (mock)
                System.out.println("Challenge joined: " + challengeName);
            }
        });

        // Feedback Section
        Label feedbackLabel = new Label("Provide Feedback");
        TextArea feedbackTextArea = new TextArea();
        feedbackTextArea.setPromptText("Enter your feedback here...");
        Button submitFeedbackButton = new Button("Submit Feedback");

        submitFeedbackButton.setOnAction(_ -> {
            String feedback = feedbackTextArea.getText().trim();
            if (feedback.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Empty Feedback", "Please enter your feedback before submitting.");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Feedback Submitted", "Thank you for your feedback!");

                // Store feedback in the database (mock)
                System.out.println("Feedback submitted: " + feedback);
                feedbackTextArea.clear();
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(_ -> primaryStage.setScene(new LoginApp().createHomeScene(primaryStage)));

        // Layout
        VBox layout = new VBox(20, titleLabel,
                new Label("Challenges"), challengesTable, joinChallengeButton,
                feedbackLabel, feedbackTextArea, submitFeedbackButton,
                backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 800, 600);
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
