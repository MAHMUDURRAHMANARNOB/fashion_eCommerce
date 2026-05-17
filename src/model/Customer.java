package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private ShoppingCart shoppingCart;
    private List<Order> orders;

    public Customer(String userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.shoppingCart = new ShoppingCart();
        this.orders = new ArrayList<>();
    }

    public List<Product> browseProducts() {
        // Will be populated from controller / repository
        return new ArrayList<>();
    }

    public void addToCart(ProductVariant variant, int quantity) {
        shoppingCart.addItem(variant, quantity);
    }

    public void removeFromCart(CartItem item) {
        shoppingCart.removeItem(item);
    }

    public Order checkout(ShippingAddress shippingAddress) {
        if (shoppingCart.getItems().isEmpty()) {
            return null;
        }
        Order order = new Order("ORD" + System.currentTimeMillis(), this, shoppingCart, shippingAddress);
        orders.add(order);
        shoppingCart.clearCart();
        return order;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public List<Order> getOrders() {
        return orders;
    }
}