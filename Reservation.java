package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reservation {
    private final IntegerProperty reservationID;
    private final StringProperty className;
    private final StringProperty schedule;

    public Reservation(int reservationID, String className, String schedule) {
        this.reservationID = new SimpleIntegerProperty(reservationID);
        this.className = new SimpleStringProperty(className);
        this.schedule = new SimpleStringProperty(schedule);
    }

    public int getReservationID() {
        return reservationID.get();
    }

    public IntegerProperty reservationIDProperty() {
        return reservationID;
    }

    public String getClassName() {
        return className.get();
    }

    public StringProperty classNameProperty() {
        return className;
    }

    public String getSchedule() {
        return schedule.get();
    }

    public StringProperty scheduleProperty() {
        return schedule;
    }
}
