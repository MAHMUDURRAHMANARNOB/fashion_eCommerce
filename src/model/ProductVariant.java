package model;

public class ProductVariant {
    private String variantId;
    private String size;
    private String color;
    private double price;
    private int stockQuantity;

    public ProductVariant(String variantId, String size, String color, double price, int stockQuantity) {
        this.variantId = variantId;
        this.size = size;
        this.color = color;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean reduceStock(int quantity) {
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    // Getters
    public String getVariantId() { return variantId; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
}