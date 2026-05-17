package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(ProductVariant variant, int quantity) {
        // Check if item already exists
        for (CartItem item : items) {
            if (item.getVariant().getVariantId().equals(variant.getVariantId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(variant, quantity));
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubTotal();
        }
        return total;
    }

    public void clearCart() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }
}