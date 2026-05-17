package model;

public abstract class Payment {
    private String paymentId;
    private double amount;
    private String status;

    public Payment(String paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = "PENDING";
    }

    public abstract boolean processPayment();

    // Getters
    public String getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }

    protected void setStatus(String status) {
        this.status = status;
    }
}