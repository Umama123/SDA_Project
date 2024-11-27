package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookFitness {

    private DBConnection dbConnection = new DBConnection(); // Use your existing DBConnection class

    public Scene createBookFitnessScene(Stage primaryStage) {
        ObservableList<FitnessClass> classList = FXCollections.observableArrayList();
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

        try {
            // Fetch available fitness classes
            List<FitnessClass> fitnessClasses = getFitnessClassesFromDB();
            classList.setAll(fitnessClasses);

            // Fetch booked reservations for the logged-in user
            List<Reservation> reservations = dbConnection.getReservations("LoggedInUser"); // Replace with actual username
            reservationList.setAll(reservations);
        } catch (Exception e) {
            showErrorAlert("Error fetching data from the database", e.getMessage());
        }

        // Create TableView for available fitness classes
        TableView<FitnessClass> classTableView = new TableView<>();
        TableColumn<FitnessClass, String> classNameColumn = new TableColumn<>("Class Name");
        classNameColumn.setCellValueFactory(data -> data.getValue().classNameProperty());

        TableColumn<FitnessClass, String> scheduleColumn = new TableColumn<>("Schedule");
        scheduleColumn.setCellValueFactory(data -> data.getValue().scheduleProperty());

        TableColumn<FitnessClass, Integer> capacityColumn = new TableColumn<>("Available Spots");
        capacityColumn.setCellValueFactory(data -> data.getValue().capacityProperty().asObject());

        
        classTableView.getColumns().add(classNameColumn);
        classTableView.getColumns().add(scheduleColumn);
        classTableView.getColumns().add(capacityColumn);

        classTableView.setItems(classList);

        // Create TableView for reservations
        TableView<Reservation> reservationTableView = new TableView<>();
        TableColumn<Reservation, String> reservedClassColumn = new TableColumn<>("Class Name");
        reservedClassColumn.setCellValueFactory(data -> data.getValue().classNameProperty());

        TableColumn<Reservation, String> reservedScheduleColumn = new TableColumn<>("Schedule");
        reservedScheduleColumn.setCellValueFactory(data -> data.getValue().scheduleProperty());

        TableColumn<Reservation, Integer> reservationIdColumn = new TableColumn<>("Reservation ID");
        reservationIdColumn.setCellValueFactory(data -> data.getValue().reservationIDProperty().asObject());

        reservationTableView.getColumns().add(reservedClassColumn);
        reservationTableView.getColumns().add(reservedScheduleColumn);
        reservationTableView.getColumns().add(reservationIdColumn);

       

        reservationTableView.setItems(reservationList);

        // Buttons for actions
        Button bookButton = new Button("Book Class");
        Button cancelButton = new Button("Cancel Reservation");
        Button backButton = new Button("Back");

        // Book class button action
        bookButton.setOnAction(_ -> {
            FitnessClass selectedClass = classTableView.getSelectionModel().getSelectedItem();
            if (selectedClass != null) {
                try {
                    boolean success = bookClassInDB(selectedClass.getClassID(), "LoggedInUser"); // Replace with actual username
                    if (success) {
                        showSuccessAlert("Class booked successfully!");
                        // Refresh reservations
                        reservationList.setAll(dbConnection.getReservations("LoggedInUser"));
                    } else {
                        showErrorAlert("Class booking failed", "Try again.");
                    }
                } catch (Exception e) {
                    showErrorAlert("Error booking class", e.getMessage());
                }
            } else {
                showWarningAlert("Please select a class to book.");
            }
        });

        // Cancel reservation button action
        cancelButton.setOnAction(_ -> {
            Reservation selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                try {
                    boolean success = cancelReservation(selectedReservation.getReservationID());
                    if (success) {
                        showSuccessAlert("Reservation canceled successfully!");
                        // Refresh reservations
                        reservationList.setAll(dbConnection.getReservations("LoggedInUser"));
                    } else {
                        showErrorAlert("Cancellation failed", "Try again.");
                    }
                } catch (Exception e) {
                    showErrorAlert("Error canceling reservation", e.getMessage());
                }
            } else {
                showWarningAlert("Please select a reservation to cancel.");
            }
        });

        // Back button action
        backButton.setOnAction(_ -> {
            Scene homeScene = new LoginApp().createHomeScene(primaryStage);
            primaryStage.setScene(homeScene);
        });

        // Layout
        VBox vbox = new VBox(10,
                new Label("Available Classes"), classTableView,
                new Label("Your Reservations"), reservationTableView,
                bookButton, cancelButton, backButton
        );
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        return new Scene(vbox, 800, 600);
    }

    // Fetch available fitness classes from the database
    private List<FitnessClass> getFitnessClassesFromDB() throws SQLException {
        return dbConnection.getFitnessClasses();
    }

    // Book a class in the database
    private boolean bookClassInDB(int classID, String username) throws SQLException {
        return dbConnection.bookClass(classID, username);
    }

    // Cancel a reservation in the database
    private boolean cancelReservation(int reservationID) throws SQLException {
        String query = "DELETE FROM booked_classes WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, reservationID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if the reservation was successfully deleted
        }
    }

    // Utility methods to show alerts
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.showAndWait();
    }

    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
