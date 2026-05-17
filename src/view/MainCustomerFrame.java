// package view;

// import model.*;
// import controller.ECommerceController;

// import javax.swing.*;
// import java.awt.*;
// import java.util.List;

// public class MainCustomerFrame extends JFrame {

//     private Customer customer;
//     private ECommerceController controller;
//     private JList<Product> productList;
//     private DefaultListModel<Product> productListModel;
//     private CartPanel cartPanel;

//     public MainCustomerFrame(Customer customer, ECommerceController controller) {
//         this.customer = customer;
//         this.controller = controller;
//         initUI();
//     }

//     private void initUI() {
//         setTitle("Fashion Store - Welcome, " + customer.getName());
//         setSize(1000, 700);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);

//         JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
//         // Left Panel - Products
//         JPanel productPanel = createProductPanel();
//         splitPane.setLeftComponent(productPanel);

//         // Right Panel - Cart
//         cartPanel = new CartPanel(customer, controller, this);
//         splitPane.setRightComponent(cartPanel);

//         splitPane.setDividerLocation(550);
//         add(splitPane);
//     }

//     private JPanel createProductPanel() {
//         JPanel panel = new JPanel(new BorderLayout());
//         panel.setBorder(BorderFactory.createTitledBorder("Available Products"));

//         productListModel = new DefaultListModel<>();
//         List<Product> products = controller.getAllProducts();
//         for (Product p : products) {
//             productListModel.addElement(p);
//         }

//         productList = new JList<>(productListModel);
//         productList.setCellRenderer(new ProductListRenderer());

//         JButton addToCartBtn = new JButton("Add to Cart");
//         addToCartBtn.addActionListener(e -> addSelectedToCart());

//         panel.add(new JScrollPane(productList), BorderLayout.CENTER);
//         panel.add(addToCartBtn, BorderLayout.SOUTH);

//         return panel;
//     }

//     private void addSelectedToCart() {
//         Product selectedProduct = productList.getSelectedValue();
//         if (selectedProduct == null) {
//             JOptionPane.showMessageDialog(this, "Please select a product");
//             return;
//         }

//         List<ProductVariant> variants = selectedProduct.getVariants();
//         if (variants.isEmpty()) return;

//         // Simple variant selection (first one for demo)
//         ProductVariant variant = variants.get(0);
//         String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity:", "1");

//         try {
//             int quantity = Integer.parseInt(qtyStr);
//             if (quantity > 0) {
//                 customer.addToCart(variant, quantity);
//                 cartPanel.refreshCart();
//                 JOptionPane.showMessageDialog(this, "Added to cart!");
//             }
//         } catch (Exception ex) {
//             JOptionPane.showMessageDialog(this, "Invalid quantity");
//         }
//     }
// }

// // Custom Renderer for Product List
// class ProductListRenderer extends DefaultListCellRenderer {
//     @Override
//     public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//         super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//         if (value instanceof Product) {
//             Product p = (Product) value;
//             setText(p.getName() + " - $" + p.getBasePrice());
//         }
//         return this;
//     }
// }

package view;

import model.*;
import controller.ECommerceController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainCustomerFrame extends JFrame {

    private Customer customer;
    private ECommerceController controller;
    private CartPanel cartPanel;

    public MainCustomerFrame(Customer customer, ECommerceController controller) {
        this.customer = customer;
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Fashion Store - Welcome, " + customer.getName());
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Left - Products
        JPanel productPanel = createImprovedProductPanel();
        splitPane.setLeftComponent(productPanel);

        // Right - Cart
        cartPanel = new CartPanel(customer, controller, this);
        splitPane.setRightComponent(cartPanel);

        splitPane.setDividerLocation(620);
        add(splitPane);

        // Menu Bar with Logout
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Account");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> logout());
        menu.add(logoutItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private JPanel createImprovedProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("🛍️ Available Products"));

        DefaultListModel<Product> model = new DefaultListModel<>();
        controller.getAllProducts().forEach(model::addElement);

        JList<Product> productList = new JList<>(model);
        productList.setCellRenderer(new ProductListRenderer());   // Fixed

        JButton addToCartBtn = new JButton("🛒 Add to Cart");
        addToCartBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addToCartBtn.addActionListener(e -> addSelectedToCart(productList));

        panel.add(new JScrollPane(productList), BorderLayout.CENTER);
        panel.add(addToCartBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void addSelectedToCart(JList<Product> productList) {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a product first!");
            return;
        }

        List<ProductVariant> variants = selected.getVariants();
        if (variants.isEmpty()) return;

        ProductVariant variant = (ProductVariant) JOptionPane.showInputDialog(this,
                "Select Variant:", "Choose Variant",
                JOptionPane.QUESTION_MESSAGE, null, variants.toArray(), variants.get(0));

        if (variant != null) {
            String qty = JOptionPane.showInputDialog(this, "Enter Quantity:", "1");
            try {
                int quantity = Integer.parseInt(qty);
                if (quantity > 0 && quantity <= variant.getStockQuantity()) {
                    customer.addToCart(variant, quantity);
                    cartPanel.refreshCart();
                    JOptionPane.showMessageDialog(this, "✅ Added to cart successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid quantity or out of stock!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity!");
            }
        }
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }

    // ==================== INNER CLASS ====================
    class ProductListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Product) {
                Product p = (Product) value;
                String text = String.format("%s - $%.2f", p.getName(), p.getBasePrice());
                setText(text);
                
                // Optional: Make it look nicer
                setFont(new Font("Arial", Font.PLAIN, 14));
            }
            return this;
        }
    }
}