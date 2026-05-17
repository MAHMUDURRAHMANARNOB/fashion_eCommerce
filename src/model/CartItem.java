package model;

public class CartItem {
    private ProductVariant variant;
    private int quantity;

    public CartItem(ProductVariant variant, int quantity) {
        this.variant = variant;
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return variant.getPrice() * quantity;
    }

    // Getters and Setters
    public ProductVariant getVariant() { return variant; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}