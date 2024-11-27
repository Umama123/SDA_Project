package application; // Ensure the package matches your project structure

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;




public class FitnessClass {
    private final SimpleIntegerProperty classID;
    private final SimpleStringProperty className;
    private final SimpleStringProperty schedule;
    private final SimpleIntegerProperty capacity;

    public FitnessClass(int classID, String className, String schedule, int capacity) {
        this.classID = new SimpleIntegerProperty(classID);
        this.className = new SimpleStringProperty(className);
        this.schedule = new SimpleStringProperty(schedule);
        this.capacity = new SimpleIntegerProperty(capacity);
    }

    public int getClassID() {
        return classID.get();
    }

    public String getClassName() {
        return className.get();
    }

    public String getSchedule() {
        return schedule.get();
    }

    public int getCapacity() {
        return capacity.get();
    }

    public SimpleStringProperty classNameProperty() {
        return className;
    }

    public SimpleStringProperty scheduleProperty() {
        return schedule;
    }

    public SimpleIntegerProperty capacityProperty() {
        return capacity;
    }
}

