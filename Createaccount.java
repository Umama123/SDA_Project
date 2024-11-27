package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Createaccount {

    private Members members = new Members(); // Members class for database interaction

    public Scene createAccountScene(Stage primaryStage) {
        // UI elements for account creation
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");

        Label dobLabel = new Label("Date of Birth:");
        DatePicker dobPicker = new DatePicker();

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label joiningDateLabel = new Label("Joining Date:");
        DatePicker joiningDatePicker = new DatePicker(LocalDate.now());

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");
        Label messageLabel = new Label();

        // Submit button action
        submitButton.setOnAction(_ -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String gender = genderComboBox.getValue();
            LocalDate dateOfBirth = dobPicker.getValue();
            String phoneNumber = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            LocalDate joiningDate = joiningDatePicker.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || gender == null || password.isEmpty() || email.isEmpty() || joiningDate == null) {
                messageLabel.setText("All required fields must be filled.");
                messageLabel.setStyle("-fx-text-fill: red;");
            } else {
                boolean success = members.createUserAccount(firstName, lastName, gender, dateOfBirth, phoneNumber, email, null, joiningDate, password);

                if (success) {
                    messageLabel.setText("Account created successfully!");
                    messageLabel.setStyle("-fx-text-fill: green;");
                    // Navigate back to Login Page
                    Scene loginScene = new LoginApp().createLoginScene(primaryStage);
                    primaryStage.setScene(loginScene);
                } else {
                    messageLabel.setText("Account creation failed. Try again.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            }
        });

        // Back button action
        backButton.setOnAction(_ -> {
            Scene loginScene = new LoginApp().createLoginScene(primaryStage);
            primaryStage.setScene(loginScene);
        });

        // Create layout for Create Account Page
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        // Add elements to the grid
        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(lastNameLabel, 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(genderLabel, 0, 2);
        grid.add(genderComboBox, 1, 2);
        grid.add(dobLabel, 0, 3);
        grid.add(dobPicker, 1, 3);
        grid.add(phoneLabel, 0, 4);
        grid.add(phoneField, 1, 4);
        grid.add(emailLabel, 0, 5);
        grid.add(emailField, 1, 5);
        grid.add(passwordLabel, 0, 6);
        grid.add(passwordField, 1, 6);
        grid.add(joiningDateLabel, 0, 7);
        grid.add(joiningDatePicker, 1, 7);
        grid.add(submitButton, 1, 8);
        grid.add(backButton, 1, 9);
        grid.add(messageLabel, 1, 10);

        // Return the scene for Create Account page
        return new Scene(grid, 600, 600);
    }
}
