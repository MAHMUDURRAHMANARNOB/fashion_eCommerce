package controller;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class ECommerceController {
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    public void initializeSampleData() {
        // Sample Users
        Customer cust = new Customer("C001", "Mahmudur Rahman", "customer@gmail.com", "123");
        Admin admin = new Admin("A001", "Ahnaf Rashid", "admin@gmail.com", "123");
        Seller seller = new Seller("S001", "Seller Brand", "seller@gmail.com", "123");

        users.add(cust);
        users.add(admin);
        users.add(seller);

        // Sample Products
        Product p1 = new Product("P001", "Nike Air Max", "Comfortable running shoes", 149.99);
        ProductVariant v1 = new ProductVariant("V001", "42", "Black", 149.99, 50);
        ProductVariant v2 = new ProductVariant("V002", "43", "White", 149.99, 30);
        p1.addVariant(v1);
        p1.addVariant(v2);
        products.add(p1);

        Product p2 = new Product("P002", "Adidas Hoodie", "Premium cotton hoodie", 89.99);
        ProductVariant v3 = new ProductVariant("V003", "M", "Red", 89.99, 40);
        p2.addVariant(v3);
        products.add(p2);

        Product p3 = new Product("P003", "Reebok Pants", "Comfortable workout pants", 59.99);
        ProductVariant v4 = new ProductVariant("V004", "L", "Blue", 59.99, 25);
        p3.addVariant(v4);
        products.add(p3);
    }

    public User login(String email, String password, String userType) {
        for (User user : users) {
            if (user.login(email, password)) {
                if ((userType.equals("Customer") && user instanceof Customer) ||
                    (userType.equals("Admin") && user instanceof Admin) ||
                    (userType.equals("Seller") && user instanceof Seller)) {
                    return user;
                }
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}