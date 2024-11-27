package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ContactTrainer {

    private Members members = new Members(); // Members class to fetch trainer details

    public Scene createContactTrainerScene(Stage primaryStage) {
        // Fetch trainer details from the database
        List<Trainer> trainers = members.getTrainers(); // Make sure this returns a List<Trainer>
        if (trainers == null || trainers.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Trainers Found");
            alert.setContentText("Unable to fetch trainers from the database.");
            alert.showAndWait();
            return null; // Exit if no trainers are found
        }

        ObservableList<Trainer> trainerList = FXCollections.observableArrayList(trainers);

        // Create TableView to display trainer details
        TableView<Trainer> tableView = new TableView<>();

        // Create columns
        TableColumn<Trainer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(150);

        TableColumn<Trainer, String> specializationColumn = new TableColumn<>("Specialization");
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        specializationColumn.setMinWidth(150);

        TableColumn<Trainer, String> contactInfoColumn = new TableColumn<>("Contact Info");
        contactInfoColumn.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        contactInfoColumn.setMinWidth(200);

        // Add columns to the table
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(specializationColumn);
        tableView.getColumns().add(contactInfoColumn);

        // Set the data for the table
        tableView.setItems(trainerList);

        // Create a button to contact the trainer
        Button contactButton = new Button("Contact Trainer");
        contactButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 10px 15px;");
        contactButton.setOnAction(_ -> {
            Trainer selectedTrainer = tableView.getSelectionModel().getSelectedItem();
            if (selectedTrainer != null) {
                // Show an alert with contact information
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Trainer Contact");
                alert.setHeaderText("Contact Trainer");
                alert.setContentText("Contact " + selectedTrainer.getName() + " at " + selectedTrainer.getContactInfo());
                alert.showAndWait();
            } else {
                // Warn if no trainer is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Trainer Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a trainer to contact.");
                alert.showAndWait();
            }
        });

        // Create a back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 10px 15px;");
        backButton.setOnAction(_ -> primaryStage.setScene(new LoginApp().createHomeScene(primaryStage)));

        // Layout
        VBox vbox = new VBox(20, tableView, contactButton, backButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        return new Scene(vbox, 600, 400);
    }
}
