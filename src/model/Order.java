package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private Date orderDate;
    private String status;
    private double totalAmount;
    private Customer customer;
    private ShippingAddress shippingAddress;
    private List<OrderItem> orderItems;
    private Payment payment;

    public Order(String orderId, Customer customer, ShoppingCart cart, ShippingAddress shippingAddress) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = new Date();
        this.status = "PENDING";
        this.shippingAddress = shippingAddress;
        this.orderItems = new ArrayList<>();

        // Convert CartItems to OrderItems
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(cartItem.getQuantity(), 
                                              cartItem.getVariant().getPrice() * cartItem.getQuantity(), 
                                              cartItem.getVariant());
            orderItems.add(orderItem);
        }

        this.totalAmount = cart.calculateTotal();
    }

    public boolean confirmOrder() {
        this.status = "CONFIRMED";
        return true;
    }

    public double calculateTotal() {
        return totalAmount;
    }

    // Getters
    public String getOrderId() { return orderId; }
    public Date getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public Customer getCustomer() { return customer; }
    public ShippingAddress getShippingAddress() { return shippingAddress; }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}