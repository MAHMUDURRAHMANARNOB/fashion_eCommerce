package model;

public class Seller extends User {

    public Seller(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    public void manageProducts() {
        System.out.println("Seller " + getName() + " is managing products.");
    }

    public void manageInventory(ProductVariant variant, int quantity) {
        variant.reduceStock(quantity);  // Example usage
        System.out.println("Updated inventory for variant: " + variant.getVariantId());
    }

    public void viewSalesAnalytics() {
        System.out.println("Viewing sales analytics...");
    }
}