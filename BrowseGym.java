package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class BrowseGym {

    private Members members = new Members(); // Initialize Members class to fetch data

    public Scene createBrowseGymServicesScene(Stage primaryStage) {
        // Fetch gym services from the database
        List<GymServices> gymServices = members.getGymServices();
        ObservableList<GymServices> servicesList = FXCollections.observableArrayList(gymServices);

        // Create TableView to display services
        TableView<GymServices> tableView = new TableView<>();

        // Define table columns
        TableColumn<GymServices, String> nameColumn = new TableColumn<>("Service Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        nameColumn.setMinWidth(150);

        TableColumn<GymServices, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setMinWidth(300);

        TableColumn<GymServices, Double> priceColumn = new TableColumn<>("Price ($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setMinWidth(100);

        TableColumn<GymServices, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        durationColumn.setMinWidth(100);

        // Add columns to the TableView
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(descriptionColumn);
        tableView.getColumns().add(priceColumn);
        tableView.getColumns().add(durationColumn);

        // Set data for the TableView
        tableView.setItems(servicesList);

        // Create a "View Details" button
        Button viewDetailsButton = new Button("View Details");
        Label detailsLabel = new Label(); // Label to display selected service details

        viewDetailsButton.setOnAction(_ -> {
            // Get the selected service
            GymServices selectedService = tableView.getSelectionModel().getSelectedItem();
            if (selectedService != null) {
                // Display service details
                detailsLabel.setText(
                        "Service Name: " + selectedService.getServiceName() + "\n" +
                        "Description: " + selectedService.getDescription() + "\n" +
                        "Price: $" + selectedService.getPrice() + "\n" +
                        "Duration: " + selectedService.getDuration()
                );
            } else {
                detailsLabel.setText("Please select a service to view details.");
            }
        });

        // Create a Back button to navigate back to the Home page
        Button backButton = new Button("Back");
        backButton.setOnAction(_-> {
            // Navigate back to the Home page
            Scene homeScene = new LoginApp().createHomeScene(primaryStage);
            primaryStage.setScene(homeScene);
        });

        // Layout for buttons and label
        HBox bottomBox = new HBox(10, backButton, viewDetailsButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        // Create main layout
        BorderPane layout = new BorderPane();
        layout.setCenter(tableView);
        layout.setBottom(bottomBox);

        // Add details label
        layout.setTop(detailsLabel);
        BorderPane.setMargin(detailsLabel, new Insets(10));

        // Return the scene
        return new Scene(layout, 800, 500);
    }
}
