// package view;

// import model.Admin;
// import controller.ECommerceController;

// import javax.swing.*;
// import java.awt.*;

// public class AdminFrame extends JFrame {

//     private Admin admin;
//     private ECommerceController controller;

//     public AdminFrame(Admin admin, ECommerceController controller) {
//         this.admin = admin;
//         this.controller = controller;
//         initUI();
//     }

//     private void initUI() {
//         setTitle("Admin Panel - " + admin.getName());
//         setSize(700, 500);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new GridLayout(3, 1, 10, 10));

//         JButton manageUsersBtn = new JButton("Manage Users");
//         JButton viewAnalyticsBtn = new JButton("View Site Analytics");
//         JButton logoutBtn = new JButton("Logout");

//         manageUsersBtn.addActionListener(e -> 
//             JOptionPane.showMessageDialog(this, "User Management Feature (Demo)"));

//         viewAnalyticsBtn.addActionListener(e -> 
//             JOptionPane.showMessageDialog(this, "Analytics:\nTotal Users: 3\nTotal Orders: 5\nRevenue: $2450"));

//         logoutBtn.addActionListener(e -> {
//             dispose();
//             new LoginFrame().setVisible(true);
//         });

//         add(new JLabel("Admin Dashboard", SwingConstants.CENTER));
//         add(manageUsersBtn);
//         add(viewAnalyticsBtn);
//         add(logoutBtn);
//     }
// }

package view;

import model.Admin;
import controller.ECommerceController;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    private Admin admin;
    private ECommerceController controller;

    public AdminFrame(Admin admin, ECommerceController controller) {
        this.admin = admin;
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Admin Panel - " + admin.getName());
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 15, 15));

        JButton manageUsersBtn = new JButton("👥 Manage Users");
        JButton analyticsBtn = new JButton("📊 View Site Analytics");
        JButton logoutBtn = new JButton("Logout");

        manageUsersBtn.addActionListener(e -> manageUsers());
        analyticsBtn.addActionListener(e -> showAnalytics());
        logoutBtn.addActionListener(e -> logout());

        add(new JLabel("Admin Dashboard", SwingConstants.CENTER));
        add(manageUsersBtn);
        add(analyticsBtn);
        add(logoutBtn);
    }

    private void manageUsers() {
        String message = "👥 User Management\n"
                + "\n"
                + "Total Customers: 12\n"
                + "Total Sellers: 4\n"
                + "Total Admins: 2\n"
                + "\n"
                + "(Full CRUD functionality can be extended here)";
        JOptionPane.showMessageDialog(this, message, "Manage Users", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAnalytics() {
        String message = "📊 Site Analytics\n"
                + "\n"
                + "Total Users      : 18\n"
                + "Total Orders     : 47\n"
                + "Total Revenue    : $12,845.75\n"
                + "Avg Order Value  : $273.10\n"
                + "Most Popular Item: Nike Air Max\n"
                + "\n"
                + "System Status: ✅ Healthy";
        JOptionPane.showMessageDialog(this, message, "Site Analytics", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}