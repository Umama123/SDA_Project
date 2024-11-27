
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

public class MembershipDetails
 {

    private Members members = new Members(); // To fetch data from database

    public Scene createMembershipDetailsScene(Stage primaryStage) {
        // Fetch membership plans
        List<MembershipPlan> membershipPlans = members.getPlans();
        ObservableList<MembershipPlan> plansList = FXCollections.observableArrayList(membershipPlans);

        // Create TableView to display membership plans
        TableView<MembershipPlan> tableView = new TableView<>();

        // Define columns
        TableColumn<MembershipPlan, String> planTypeColumn = new TableColumn<>("Plan Type");
        planTypeColumn.setCellValueFactory(new PropertyValueFactory<>("planType"));
        planTypeColumn.setMinWidth(150);

        TableColumn<MembershipPlan, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        durationColumn.setMinWidth(100);

        TableColumn<MembershipPlan, Double> priceColumn = new TableColumn<>("Price ($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setMinWidth(100);

        TableColumn<MembershipPlan, String> paymentOptionsColumn = new TableColumn<>("Payment Options");
        paymentOptionsColumn.setCellValueFactory(new PropertyValueFactory<>("paymentOptions"));
        paymentOptionsColumn.setMinWidth(200);

        // Add columns to table
        
        tableView.getColumns().add(planTypeColumn);
        tableView.getColumns().add(durationColumn);
        tableView.getColumns().add(priceColumn);
        tableView.getColumns().add(paymentOptionsColumn);



       

        // Set data for the table
        tableView.setItems(plansList);

        // Create Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(_ -> {
            Scene homeScene = new LoginApp().createHomeScene(primaryStage);
            primaryStage.setScene(homeScene);
        });

        // Layout
        HBox bottomBox = new HBox(backButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        BorderPane layout = new BorderPane();
        layout.setCenter(tableView);
        layout.setBottom(bottomBox);

        return new Scene(layout, 800, 500);
    }
}
