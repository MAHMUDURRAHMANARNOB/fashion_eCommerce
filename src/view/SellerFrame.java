// package view;

// import model.Seller;
// import model.Product;
// import model.ProductVariant;
// import controller.ECommerceController;

// import javax.swing.*;
// import java.awt.*;

// public class SellerFrame extends JFrame {

//     private Seller seller;
//     private ECommerceController controller;

//     public SellerFrame(Seller seller, ECommerceController controller) {
//         this.seller = seller;
//         this.controller = controller;
//         initUI();
//     }

//     private void initUI() {
//         setTitle("Seller Panel - " + seller.getName());
//         setSize(700, 500);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new GridLayout(4, 1, 10, 10));

//         JButton manageProductsBtn = new JButton("Manage Products");
//         JButton manageInventoryBtn = new JButton("Manage Inventory");
//         JButton viewSalesBtn = new JButton("View Sales Analytics");
//         JButton logoutBtn = new JButton("Logout");

//         manageProductsBtn.addActionListener(e -> 
//             JOptionPane.showMessageDialog(this, "Product Management (Add/Edit) - Demo"));

//         manageInventoryBtn.addActionListener(e -> {
//             JOptionPane.showMessageDialog(this, "Inventory Updated Successfully!");
//         });

//         viewSalesBtn.addActionListener(e -> 
//             JOptionPane.showMessageDialog(this, "Sales Analytics:\nTotal Sales: $1850\nTop Product: Nike Air Max"));

//         logoutBtn.addActionListener(e -> {
//             dispose();
//             new LoginFrame().setVisible(true);
//         });

//         add(new JLabel("Seller Dashboard", SwingConstants.CENTER));
//         add(manageProductsBtn);
//         add(manageInventoryBtn);
//         add(viewSalesBtn);
//         add(logoutBtn);
//     }
// }

package view;

import model.*;
import controller.ECommerceController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SellerFrame extends JFrame {

    private Seller seller;
    private ECommerceController controller;

    public SellerFrame(Seller seller, ECommerceController controller) {
        this.seller = seller;
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Seller Panel - " + seller.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Seller Dashboard", SwingConstants.CENTER));
        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton addProductBtn = new JButton("Add New Product");
        JButton manageProductsBtn = new JButton("Manage Products");
        JButton viewInventoryBtn = new JButton("Show Inventory");
        JButton salesAnalyticsBtn = new JButton("Sales Analytics");
        JButton logoutBtn = new JButton("Logout");

        addProductBtn.addActionListener(e -> addNewProduct());
        manageProductsBtn.addActionListener(e -> manageProducts());
        viewInventoryBtn.addActionListener(e -> showInventory());
        salesAnalyticsBtn.addActionListener(e -> showSalesAnalytics());
        logoutBtn.addActionListener(e -> logout());

        buttonPanel.add(addProductBtn);
        buttonPanel.add(manageProductsBtn);
        buttonPanel.add(viewInventoryBtn);
        buttonPanel.add(salesAnalyticsBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addNewProduct() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField descField = new JTextField();

        Object[] message = {
            "Product Name:", nameField,
            "Description:", descField,
            "Base Price ($):", priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Product", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String desc = descField.getText();
                double price = Double.parseDouble(priceField.getText());

                Product newProduct = new Product("P" + System.currentTimeMillis(), name, desc, price);
                controller.addProduct(newProduct);
                JOptionPane.showMessageDialog(this, "Product added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }

    private void manageProducts() {
        JOptionPane.showMessageDialog(this, "Full Product Management (Add/Edit/Delete) coming in next version.\nCurrently supports Add Product.");
    }

    private void showInventory() {
        StringBuilder sb = new StringBuilder("=== Current Inventory ===\n\n");
        for (Product p : controller.getAllProducts()) {
            sb.append(p.getName()).append("\n");
            for (ProductVariant v : p.getVariants()) {
                sb.append("   • ").append(v.getColor()).append(" / ").append(v.getSize())
                  .append(" → Stock: ").append(v.getStockQuantity()).append("\n");
            }
            sb.append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Inventory", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSalesAnalytics() {
        String analyticsText = "📊 Sales Analytics\n\n"
                + "Total Revenue: $2,847.50\n"
                + "Total Orders: 18\n"
                + "Top Selling Product: Nike Air Max\n"
                + "Top Color: Black\n"
                + "Conversion Rate: 68%\n\n"
                + "Performance: Excellent 🎉\n";
        JOptionPane.showMessageDialog(this, analyticsText, "Sales Analytics", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}