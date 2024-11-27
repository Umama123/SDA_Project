package application;
public class MembershipPlan {
    private int planID;
    private String planType;
    private String duration;
    private double price;
    private String paymentOptions;

    // Constructor
    public MembershipPlan(int planID, String planType, String duration, double price, String paymentOptions) {
        this.planID = planID;
        this.planType = planType;
        this.duration = duration;
        this.price = price;
        this.paymentOptions = paymentOptions;
    }

    // Getters
    public int getPlanID() {
        return planID;
    }

    public String getPlanType() {
        return planType;
    }

    public String getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public String getPaymentOptions() {
        return paymentOptions;
    }

    // Setters
    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPaymentOptions(String paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    // Override toString() for better readability
    @Override
    public String toString() {
        return "Plan{" +
                "planID=" + planID +
                ", planType='" + planType + '\'' +
                ", duration='" + duration + '\'' +
                ", price=" + price +
                ", paymentOptions='" + paymentOptions + '\'' +
                '}';
    }
}
