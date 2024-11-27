package application;

import javafx.beans.property.*;

public class WorkoutSession {

    private final StringProperty workoutType;
    private final IntegerProperty duration;
    private final IntegerProperty caloriesBurned;
    private final StringProperty date;

    // Constructor
    public WorkoutSession(String workoutType, int duration, int caloriesBurned, String date) {
        this.workoutType = new SimpleStringProperty(workoutType);
        this.duration = new SimpleIntegerProperty(duration);
        this.caloriesBurned = new SimpleIntegerProperty(caloriesBurned);
        this.date = new SimpleStringProperty(date);
    }

    // Getters for TableView binding
    public StringProperty workoutTypeProperty() {
        return workoutType;
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public IntegerProperty caloriesBurnedProperty() {
        return caloriesBurned;
    }

    public StringProperty dateProperty() {
        return date;
    }

    // Regular Getters
    public String getWorkoutType() {
        return workoutType.get();
    }

    public int getDuration() {
        return duration.get();
    }

    public int getCaloriesBurned() {
        return caloriesBurned.get();
    }

    public String getDate() {
        return date.get();
    }

    // Regular Setters
    public void setWorkoutType(String workoutType) {
        this.workoutType.set(workoutType);
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned.set(caloriesBurned);
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
