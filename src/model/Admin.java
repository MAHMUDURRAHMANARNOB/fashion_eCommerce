package model;

import java.util.List;

public class Admin extends User {

    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    public void manageUsers() {
        System.out.println("Admin " + getName() + " is managing users.");
    }

    public void manageOrders() {
        System.out.println("Admin " + getName() + " is managing orders.");
    }

    public void viewSiteAnalytics() {
        System.out.println("Viewing site analytics...");
    }
}