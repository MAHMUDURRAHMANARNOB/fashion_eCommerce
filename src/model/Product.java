package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String productId;
    private String name;
    private String description;
    private double basePrice;
    private List<String> images;
    private List<ProductVariant> variants;

    public Product(String productId, String name, String description, double basePrice) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.images = new ArrayList<>();
        this.variants = new ArrayList<>();
    }

    public void addVariant(ProductVariant variant) {
        variants.add(variant);
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    // Getters
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getBasePrice() { return basePrice; }
    public List<String> getImages() { return images; }
}