package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gymmanage?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "1234"; // Replace with your MySQL password

    // Method to establish a connection with the database
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<FitnessClass> getFitnessClasses() throws SQLException {
        List<FitnessClass> classes = new ArrayList<>();
        String query = "SELECT class_id, class_name, schedule, capacity FROM fitness_classes";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
    
            while (resultSet.next()) {
                int classId = resultSet.getInt("class_id");
                String className = resultSet.getString("class_name");
                String schedule = resultSet.getString("schedule");
                int capacity = resultSet.getInt("capacity");
    
                System.out.println("Fetched: " + className + " | " + schedule + " | " + capacity);
    
                FitnessClass fitnessClass = new FitnessClass(classId, className, schedule, capacity);
                classes.add(fitnessClass);
            }
        }
    
        System.out.println("Total Classes Fetched: " + classes.size());
        return classes;
    }
    
    public List<Reservation> getReservations(String username) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT b.id AS reservation_id, f.class_name, f.schedule " +
                       "FROM booked_classes b " +
                       "JOIN fitness_classes f ON b.class_id = f.class_id " +
                       "WHERE b.username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setString(1, username);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int reservationID = resultSet.getInt("reservation_id");
                    String className = resultSet.getString("class_name");
                    String schedule = resultSet.getString("schedule");
    
                    reservations.add(new Reservation(reservationID, className, schedule));
                }
            }
        }
        return reservations;
    }
    

    // Method to book a fitness class
    public boolean bookClass(int classID, String username) throws SQLException {
        String query = "INSERT INTO booked_classes (class_id, username) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, classID);
            statement.setString(2, username);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was inserted
        }
    }


    public boolean cancelReservation(int reservationID) throws SQLException {
        String query = "DELETE FROM booked_classes WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setInt(1, reservationID); // Use the reservation ID to delete the record
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was deleted
        }
    }
    
}
