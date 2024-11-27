package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Members {

    private DBConnection dbConnection = new DBConnection();

    // Validates user login by checking username and password
    public boolean validateMember(String username, String password) {
        String query = "SELECT Password FROM Members WHERE FirstName = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                return storedPassword.equals(password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Creates a new user account in the database
    public boolean createUserAccount(String firstName, String lastName, String gender, LocalDate dateOfBirth,
                                      String phoneNumber, String email, String address, LocalDate joiningDate, String password) {
        String query = "INSERT INTO Members (FirstName, LastName, Gender, DateOfBirth, PhoneNumber, Email, Address, MembershipID, JoiningDate, Password) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName != null ? firstName.trim() : "");
            stmt.setString(2, lastName != null ? lastName.trim() : "");
            stmt.setString(3, gender != null ? gender.trim() : "Other");
            stmt.setDate(4, dateOfBirth != null ? java.sql.Date.valueOf(dateOfBirth) : null);
            stmt.setString(5, phoneNumber != null ? phoneNumber.trim() : "");
            stmt.setString(6, email != null ? email.trim() : "");
            stmt.setString(7, address != null ? address.trim() : "Unknown");
            stmt.setObject(8, null); // MembershipID is optional
            stmt.setDate(9, joiningDate != null ? java.sql.Date.valueOf(joiningDate) : null);
            stmt.setString(10, password != null ? password.trim() : "");

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetches all gym services from the database
    public List<GymServices> getGymServices() {
        String query = "SELECT * FROM GymServices";
        List<GymServices> services = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                services.add(new GymServices(
                    rs.getInt("ServiceID"),
                    rs.getString("ServiceName"),
                    rs.getString("Description"),
                    rs.getDouble("Price"),
                    rs.getString("Duration")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    // Fetches all membership plans from the database
    public List<MembershipPlan> getPlans() {
        String query = "SELECT * FROM gymmanage.membershipplans";
        List<MembershipPlan> plans = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                plans.add(new MembershipPlan(
                    rs.getInt("PlanID"),
                    rs.getString("PlanType"),
                    rs.getString("Duration"),
                    rs.getDouble("Price"),
                    rs.getString("PaymentOptions")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plans;
    }

    // Fetches reservations for a specific user
    public List<Reservation> getReservations(String userName) {
        String query = "SELECT r.ReservationID, f.ClassName, f.Schedule FROM Reservations r " +
                       "JOIN FitnessClasses f ON r.ClassID = f.ClassID WHERE r.UserName = ?";
        List<Reservation> reservations = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(
                    rs.getInt("ReservationID"),
                    rs.getString("ClassName"),
                    rs.getString("Schedule")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // Cancels a specific reservation by its ID
    public boolean cancelReservation(int reservationID) {
        String query = "DELETE FROM Reservations WHERE ReservationID = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reservationID);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetches all fitness classes from the database
    public List<FitnessClass> getFitnessClasses() {
        String query = "SELECT * FROM gymmanage.FitnessClasses";
        List<FitnessClass> fitnessClasses = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                fitnessClasses.add(new FitnessClass(
                    rs.getInt("ClassID"),
                    rs.getString("ClassName"),
                    rs.getString("Schedule"),
                    rs.getInt("Capacity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fitnessClasses;
    }

    // Books a fitness class for a user
    public boolean bookClass(int classID, String userName) {
        String insertReservationQuery = "INSERT INTO Reservations (ClassID, UserName) VALUES (?, ?)";
        String updateCapacityQuery = "UPDATE FitnessClasses SET Capacity = Capacity - 1 WHERE ClassID = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertReservationQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateCapacityQuery)) {

            // Insert reservation
            insertStmt.setInt(1, classID);
            insertStmt.setString(2, userName);
            insertStmt.executeUpdate();

            // Update class capacity
            updateStmt.setInt(1, classID);
            updateStmt.executeUpdate();

            return true; // Booking successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Booking failed
        }
    }

    // Fetches all trainers from the database
    
    public List<Trainer> getTrainers() {
        String query = "SELECT * FROM Trainers";
        List<Trainer> trainers = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            System.out.println("Executing trainer query..."); // Debug print
    
            while (rs.next()) {
                Trainer trainer = new Trainer(
                    rs.getInt("TrainerID"),
                    rs.getString("Name"),
                    rs.getString("Specialization"),
                    rs.getString("ContactInfo")
                );
                trainers.add(trainer);
                System.out.println("Added trainer: " + trainer.getName()); // Debug print
            }
    
            System.out.println("Total trainers found: " + trainers.size()); // Debug print
        } catch (Exception e) {
            System.out.println("Error fetching trainers: "); // Debug print
            e.printStackTrace();
        }
        return trainers;
    }

    // Logs workout sessions for a user
    public boolean logWorkout(String userName, String workoutType, int duration, int caloriesBurned)
     {
        String query = "INSERT INTO WorkoutSessions (UserName, WorkoutType, Duration, CaloriesBurned, Date) VALUES (?, ?, ?, ?, CURDATE())";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, userName);
            stmt.setString(2, workoutType);
            stmt.setInt(3, duration);
            stmt.setInt(4, caloriesBurned);
    
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Add debugging log
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return false;
        }
    }

    // Fetches all workout sessions for a specific user
    public List<WorkoutSession> getWorkoutSessions(String userName) {
        String query = "SELECT WorkoutType, Duration, CaloriesBurned, Date FROM WorkoutSessions WHERE UserName = ?";
        List<WorkoutSession> sessions = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                sessions.add(new WorkoutSession(
                    rs.getString("WorkoutType"),
                    rs.getInt("Duration"),
                    rs.getInt("CaloriesBurned"),
                    rs.getString("Date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessions;
    }
}
