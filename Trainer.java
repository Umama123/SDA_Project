package application;

public class Trainer {
    private int trainerID;
    private String name;
    private String specialization;
    private String contactInfo;

    // Constructor
    public Trainer(int trainerID, String name, String specialization, String contactInfo) {
        this.trainerID = trainerID;
        this.name = name;
        this.specialization = specialization;
        this.contactInfo = contactInfo;
    }

    // Getters - these are essential for TableView to work with PropertyValueFactory
    public int getTrainerID() { return trainerID; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getContactInfo() { return contactInfo; }
}