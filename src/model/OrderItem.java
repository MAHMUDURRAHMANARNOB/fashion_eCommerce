package model;

public class OrderItem {
    private int quantity;
    private double priceAtPurchase;
    private ProductVariant variant;

    public OrderItem(int quantity, double priceAtPurchase, ProductVariant variant) {
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.variant = variant;
    }

    // Getters
    public int getQuantity() { return quantity; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
    public ProductVariant getVariant() { return variant; }
}