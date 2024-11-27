package application;

public class GymServices {

    private int serviceID;
    private String serviceName;
    private String description;
    private double price;
    private String duration;

    public GymServices(int serviceID, String serviceName, String description, double price, String duration) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    // Getters
    public int getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getDuration() {
        return duration;
    }
}
