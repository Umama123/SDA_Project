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

public class HealthTracker {

    /**
     * Creates the Track Nutrition and Health scene.
     *
     * @param primaryStage The main stage of the application.
     * @return The Scene for tracking nutrition and health.
     */
    public Scene createTrackNutritionAndHealthScene(Stage primaryStage) {
        // Title label
        Label titleLabel = new Label("Track Nutrition and Health Status");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #00796b;");

        // Table for Nutrition
        TableView<Map<String, String>> nutritionTable = new TableView<>();
        ObservableList<Map<String, String>> nutritionData = FXCollections.observableArrayList();

        TableColumn<Map<String, String>, String> mealNameColumn = new TableColumn<>("Meal Name");
        mealNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrDefault("Meal Name", "")));
        TableColumn<Map<String, String>, String> caloriesColumn = new TableColumn<>("Calories");
        caloriesColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrDefault("Calories", "")));
        TableColumn<Map<String, String>, String> proteinColumn = new TableColumn<>("Protein");
        proteinColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrDefault("Protein", "")));

        nutritionTable.getColumns().add(mealNameColumn);
        nutritionTable.getColumns().add(caloriesColumn);
        nutritionTable.getColumns().add(proteinColumn);

        nutritionTable.setItems(nutritionData);

        // Input fields for Nutrition
        TextField mealNameField = new TextField();
        mealNameField.setPromptText("Meal Name");
        TextField caloriesField = new TextField();
        caloriesField.setPromptText("Calories");
        TextField proteinField = new TextField();
        proteinField.setPromptText("Protein");

        Button addNutritionButton = new Button("Add Nutrition");
        addNutritionButton.setOnAction(_ -> {
            Map<String, String> nutritionEntry = new HashMap<>();
            nutritionEntry.put("Meal Name", mealNameField.getText().trim());
            nutritionEntry.put("Calories", caloriesField.getText().trim());
            nutritionEntry.put("Protein", proteinField.getText().trim());
            nutritionData.add(nutritionEntry);

            // Clear fields after adding
            mealNameField.clear();
            caloriesField.clear();
            proteinField.clear();
        });

        // Table for Health Status
        TableView<Map<String, String>> healthTable = new TableView<>();
        ObservableList<Map<String, String>> healthData = FXCollections.observableArrayList();

        TableColumn<Map<String, String>, String> weightColumn = new TableColumn<>("Weight");
        weightColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrDefault("Weight", "")));
        TableColumn<Map<String, String>, String> heightColumn = new TableColumn<>("Height");
        heightColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrDefault("Height", "")));
        TableColumn<Map<String, String>, String> bloodPressureColumn = new TableColumn<>("Blood Pressure");
        bloodPressureColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrDefault("Blood Pressure", "")));

        healthTable.getColumns().add(weightColumn);
        healthTable.getColumns().add(heightColumn);
        healthTable.getColumns().add(bloodPressureColumn);

        healthTable.setItems(healthData);

        // Input fields for Health Status
        TextField weightField = new TextField();
        weightField.setPromptText("Weight");
        TextField heightField = new TextField();
        heightField.setPromptText("Height");
        TextField bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Blood Pressure");

        Button addHealthStatusButton = new Button("Add Health Status");
        addHealthStatusButton.setOnAction(_ -> {
            Map<String, String> healthEntry = new HashMap<>();
            healthEntry.put("Weight", weightField.getText().trim());
            healthEntry.put("Height", heightField.getText().trim());
            healthEntry.put("Blood Pressure", bloodPressureField.getText().trim());
            healthData.add(healthEntry);

            // Clear fields after adding
            weightField.clear();
            heightField.clear();
            bloodPressureField.clear();
        });

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(_ -> primaryStage.setScene(new LoginApp().createHomeScene(primaryStage)));

        // Layout
        VBox layout = new VBox(20, titleLabel,
                new Label("Nutrition Table"), nutritionTable, mealNameField, caloriesField, proteinField, addNutritionButton,
                new Label("Health Status Table"), healthTable, weightField, heightField, bloodPressureField, addHealthStatusButton,
                backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 800, 600);
    }
}
