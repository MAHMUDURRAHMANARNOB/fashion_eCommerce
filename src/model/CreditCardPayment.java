package model;

public class CreditCardPayment extends Payment {

    public CreditCardPayment(String paymentId, double amount) {
        super(paymentId, amount);
    }

    @Override
    public boolean processPayment() {
        // Simulate payment processing
        System.out.println("Processing Credit Card Payment of $" + getAmount());
        setStatus("COMPLETED");
        return true;
    }
}