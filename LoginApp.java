package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class LoginApp extends Application {

    private Members members = new Members(); // Initialize the Members class
    private String loggedInUserName; // Store the logged-in user's name

    @Override
    public void start(Stage primaryStage) {
        // Create the login screen
        Scene loginScene = createLoginScene(primaryStage);

        // Set the initial scene and show the stage
        primaryStage.setTitle("FITFLIX Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public Scene createLoginScene(Stage primaryStage) {
        // Load the logo
        ImageView logoImageView = new ImageView(new Image("file:/c:/Users/user/Downloads/FITZONE%20(2).png")); // Update the path to your logo
        logoImageView.setFitWidth(200); // Set the width of the logo
        logoImageView.setPreserveRatio(true); // Preserve the aspect ratio of the logo

        // Create UI elements for the login screen
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");
        Label messageLabel = new Label(); // To display login status

        // Login button action
        loginButton.setOnAction(_ -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Validate credentials using the Members class
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter both username and password.");
                messageLabel.setStyle("-fx-text-fill: red;");
            } else if (members.validateMember(username, password)) {
                loggedInUserName = username; // Save the logged-in user's name
                messageLabel.setText("Login Successful!");
                messageLabel.setStyle("-fx-text-fill: green;");

                // Navigate to the Home screen
                Scene homeScene = createHomeScene(primaryStage);
                primaryStage.setScene(homeScene);
            } else {
                messageLabel.setText("Invalid username or password.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Create Account button action
        createAccountButton.setOnAction(_ -> {
            // Navigate to Create Account Page
            Scene createAccountScene = new Createaccount().createAccountScene(primaryStage);
            primaryStage.setScene(createAccountScene);
        });

        // Create a layout for the login screen
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10); // Vertical gap between rows
        grid.setHgap(10); // Horizontal gap between columns
        grid.setPadding(new Insets(20)); // Padding around the grid

        // Add elements to the grid layout
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(createAccountButton, 1, 3);
        grid.add(messageLabel, 1, 4);

        // Combine the logo and form in a VBox
        VBox vbox = new VBox(20, logoImageView, grid);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Create a scene and attach CSS
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add("file:src/styles/styles.css"); // Attach the CSS file
        return scene;
    }

    public Scene createHomeScene(Stage primaryStage) {
        // Create UI elements for the home screen
        Label welcomeLabel = new Label("Welcome to FITFLIX, " + loggedInUserName + "");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #00796b;");

        // Major buttons
        Button manageClassesButton = new Button("Manage Classes");
        Button membershipDetailsButton = new Button("Membership Details");
        Button browseServicesButton = new Button("Browse Gym Services");
        Button trackWorkoutButton = new Button("Track Workout");
        Button accessWorkoutGuideButton = new Button("Access Workout Guides"); // Initialize the button
        Button HealthTrackerButton = new Button("Track Nutrition and Monitor Health");
        Button joinChallengeandFeedbackButton = new Button("Join Challenge ans Feedback");
        //Button provideFeedbackButton = new Button("Provide Feedback");



        Button logoutButton = new Button("Logout");

        // Styling for buttons
        manageClassesButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        membershipDetailsButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        browseServicesButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        trackWorkoutButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        accessWorkoutGuideButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        HealthTrackerButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
    
       joinChallengeandFeedbackButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
       //provideFeedbackButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        logoutButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");

        // Manage Classes button action
        manageClassesButton.setOnAction(_ -> {
            Scene manageClassesScene = createManageClassesScene(primaryStage);
            primaryStage.setScene(manageClassesScene);
        });

        // Membership Details button action
        membershipDetailsButton.setOnAction(_ -> {
            Scene membershipDetailsScene = new MembershipDetails().createMembershipDetailsScene(primaryStage);
            primaryStage.setScene(membershipDetailsScene);
        });

        // Browse Gym Services button action
        browseServicesButton.setOnAction(_ -> {
            Scene browseServicesScene = new BrowseGym().createBrowseGymServicesScene(primaryStage);
            primaryStage.setScene(browseServicesScene);
        });

        // Track Workout button action
        trackWorkoutButton.setOnAction(_ -> {
            Scene trackWorkoutScene = new TrackWorkout().createTrackWorkoutScene(primaryStage, loggedInUserName);
            primaryStage.setScene(trackWorkoutScene);
        });

        // Access Workout Guides button action
        accessWorkoutGuideButton.setOnAction(_ -> {
            // Directly display a guide using an Alert box
            Alert workoutGuideAlert = new Alert(Alert.AlertType.INFORMATION);
            workoutGuideAlert.setTitle("Workout Guides");
            workoutGuideAlert.setHeaderText("Access Workout Guides");
            workoutGuideAlert.setContentText("Here's a guide for you: \n1. Cardio - 30 mins\n2. Strength - 20 mins\n3. Yoga - 15 mins");
            workoutGuideAlert.showAndWait();
        });

         // Button actions
    HealthTrackerButton.setOnAction(_ -> {
        // Navigate to Track Nutrition and Monitor Health Scene
        Scene nutritionAndHealthScene = new HealthTracker().createTrackNutritionAndHealthScene(primaryStage);
        primaryStage.setScene(nutritionAndHealthScene);
    });


    joinChallengeandFeedbackButton.setOnAction(_ -> {
        Scene challengesAndFeedbackScene = new ChallengesAndFeedback().createChallengesAndFeedbackScene(primaryStage);
        primaryStage.setScene(challengesAndFeedbackScene);
    });
    
        // Logout button action
        logoutButton.setOnAction(_ -> primaryStage.setScene(createLoginScene(primaryStage)));

        // Home page layout
        VBox vbox = new VBox(20, welcomeLabel, manageClassesButton,membershipDetailsButton, browseServicesButton, trackWorkoutButton, accessWorkoutGuideButton, HealthTrackerButton,joinChallengeandFeedbackButton,logoutButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add("file:src/styles/styles.css");
        return scene;
    }

    public Scene createManageClassesScene(Stage primaryStage) {
        VBox manageClassesLayout = new VBox(20);
        manageClassesLayout.setAlignment(Pos.CENTER);
        manageClassesLayout.setPadding(new Insets(20));

        Button bookandcancelClassButton = new Button("Book  And Cancel Fitness Class");
       // Button cancelClassButton = new Button("Cancel Class Reservation");
        Button contactTrainerButton = new Button("Contact Trainer");
        Button backButton = new Button("Back");

        // Set button actions
        bookandcancelClassButton.setOnAction(_ -> primaryStage.setScene(new BookFitness().createBookFitnessScene(primaryStage)));
        //cancelClassButton.setOnAction(_ -> primaryStage.setScene(new CancelReservation().createCancelReservationScene(primaryStage)));
        contactTrainerButton.setOnAction(_ -> primaryStage.setScene(new ContactTrainer().createContactTrainerScene(primaryStage)));
        backButton.setOnAction(_ -> primaryStage.setScene(createHomeScene(primaryStage)));

        manageClassesLayout.getChildren().addAll(new Label("Manage Classes"), bookandcancelClassButton,  contactTrainerButton, backButton);

        return new Scene(manageClassesLayout, 400, 400);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
