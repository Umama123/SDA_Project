package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class CancelReservation {

    private DBConnection dbConnection = new DBConnection();

    public Scene createCancelReservationScene(Stage primaryStage) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

        try {
            // Fetch reservations for the logged-in user
            List<Reservation> reservations = dbConnection.getReservations("LoggedInUser"); // Replace with actual username
            reservationList.setAll(reservations);
        } catch (SQLException e) {
            showErrorAlert("Error fetching reservations from the database", e.getMessage());
        }

        // Create TableView to display reservations
        TableView<Reservation> tableView = new TableView<>();

        TableColumn<Reservation, String> classNameColumn = new TableColumn<>("Class Name");
        classNameColumn.setCellValueFactory(data -> data.getValue().classNameProperty());

        TableColumn<Reservation, String> scheduleColumn = new TableColumn<>("Schedule");
        scheduleColumn.setCellValueFactory(data -> data.getValue().scheduleProperty());

        TableColumn<Reservation, Integer> reservationIDColumn = new TableColumn<>("Reservation ID");
        reservationIDColumn.setCellValueFactory(data -> data.getValue().reservationIDProperty().asObject());

        
        tableView.getColumns().add(classNameColumn);
        tableView.getColumns().add(scheduleColumn);
        tableView.getColumns().add(reservationIDColumn);

        tableView.setItems(reservationList);

        // Buttons
        Button cancelButton = new Button("Cancel Reservation");
        Button backButton = new Button("Back");

        // Cancel button action
        cancelButton.setOnAction(_-> {
            Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                try {
                    boolean success = dbConnection.cancelReservation(selectedReservation.getReservationID());
                    if (success) {
                        showSuccessAlert("Reservation canceled successfully!");
                        // Refresh the table after cancellation
                        reservationList.setAll(dbConnection.getReservations("LoggedInUser"));
                    } else {
                        showErrorAlert("Cancellation failed", "Please try again.");
                    }
                } catch (SQLException e) {
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
        VBox vbox = new VBox(10, tableView, cancelButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        return new Scene(vbox, 600, 400);
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
