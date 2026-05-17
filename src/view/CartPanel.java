// package view;

// import model.*;
// import controller.ECommerceController;

// import javax.swing.*;
// import java.awt.*;
// import java.util.List;

// public class CartPanel extends JPanel {

//     private Customer customer;
//     private ECommerceController controller;
//     private MainCustomerFrame parentFrame;
//     private DefaultListModel<CartItem> cartModel;
//     private JList<CartItem> cartList;
//     private JLabel totalLabel;

//     public CartPanel(Customer customer, ECommerceController controller, MainCustomerFrame parent) {
//         this.customer = customer;
//         this.controller = controller;
//         this.parentFrame = parent;
//         initUI();
//     }

//     private void initUI() {
//         setLayout(new BorderLayout());
//         setBorder(BorderFactory.createTitledBorder("Shopping Cart"));

//         cartModel = new DefaultListModel<>();
//         cartList = new JList<>(cartModel);

//         JButton removeBtn = new JButton("Remove Item");
//         JButton checkoutBtn = new JButton("Checkout");
//         checkoutBtn.setBackground(new Color(0, 150, 0));
//         // checkoutBtn.setForeground(Color.WHITE);
//         checkoutBtn.setFont(new Font("Arial", Font.BOLD, 14));

//         removeBtn.addActionListener(e -> removeSelectedItem());
//         checkoutBtn.addActionListener(e -> checkout());

//         JPanel bottomPanel = new JPanel();
//         totalLabel = new JLabel("Total: $0.00");
//         totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
//         bottomPanel.add(totalLabel);
//         bottomPanel.add(removeBtn);
//         bottomPanel.add(checkoutBtn);

//         add(new JScrollPane(cartList), BorderLayout.CENTER);
//         add(bottomPanel, BorderLayout.SOUTH);

//         refreshCart();
//     }

//     public void refreshCart() {
//         cartModel.clear();
//         for (CartItem item : customer.getShoppingCart().getItems()) {
//             cartModel.addElement(item);
//         }
//         totalLabel.setText("Total: $" + String.format("%.2f", customer.getShoppingCart().calculateTotal()));
//     }

//     private void removeSelectedItem() {
//         CartItem selected = cartList.getSelectedValue();
//         if (selected != null) {
//             customer.removeFromCart(selected);
//             refreshCart();
//         }
//     }

//     private void checkout() {
//         if (customer.getShoppingCart().getItems().isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Cart is empty!");
//             return;
//         }

//         String street = JOptionPane.showInputDialog("Street Address:");
//         String city = JOptionPane.showInputDialog("City:");
//         String state = JOptionPane.showInputDialog("State:");
//         String postal = JOptionPane.showInputDialog("Postal Code:");

//         ShippingAddress address = new ShippingAddress(street, city, state, postal);
//         Order order = customer.checkout(address);

//         if (order != null) {
//             // Process Payment
//             CreditCardPayment payment = new CreditCardPayment("PAY" + System.currentTimeMillis(), order.getTotalAmount());
//             if (payment.processPayment()) {
//                 order.setPayment(payment);
//                 order.confirmOrder();
//                 JOptionPane.showMessageDialog(this, "Order placed successfully!\nOrder ID: " + order.getOrderId());
//                 refreshCart();
//             }
//         }
//     }
// }

// package view;

// import model.*;
// import controller.ECommerceController;

// import javax.swing.*;
// import java.awt.*;
// import java.util.List;

// public class CartPanel extends JPanel {

//     private Customer customer;
//     private ECommerceController controller;
//     private MainCustomerFrame parentFrame;
//     private DefaultListModel<CartItem> cartModel;
//     private JList<CartItem> cartList;
//     private JLabel totalLabel;

//     public CartPanel(Customer customer, ECommerceController controller, MainCustomerFrame parent) {
//         this.customer = customer;
//         this.controller = controller;
//         this.parentFrame = parent;
//         initUI();
//     }

//     private void initUI() {
//         setLayout(new BorderLayout());
//         setBorder(BorderFactory.createTitledBorder("🛒 Shopping Cart"));

//         cartModel = new DefaultListModel<>();
//         cartList = new JList<>(cartModel);
//         cartList.setCellRenderer(new CartItemRenderer());   // ← This fixes the display

//         JButton removeBtn = new JButton("Remove Item");
//         JButton checkoutBtn = new JButton("Proceed to Checkout");
//         checkoutBtn.setBackground(new Color(0, 150, 0));
//         checkoutBtn.setForeground(Color.WHITE);
//         checkoutBtn.setFont(new Font("Arial", Font.BOLD, 14));

//         removeBtn.addActionListener(e -> removeSelectedItem());
//         checkoutBtn.addActionListener(e -> checkout());

//         JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//         totalLabel = new JLabel("Total: $0.00");
//         totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

//         bottomPanel.add(totalLabel);
//         bottomPanel.add(removeBtn);
//         bottomPanel.add(checkoutBtn);

//         add(new JScrollPane(cartList), BorderLayout.CENTER);
//         add(bottomPanel, BorderLayout.SOUTH);

//         refreshCart();
//     }

//     public void refreshCart() {
//         cartModel.clear();
//         for (CartItem item : customer.getShoppingCart().getItems()) {
//             cartModel.addElement(item);
//         }
//         totalLabel.setText("Total: $" + String.format("%.2f", customer.getShoppingCart().calculateTotal()));
//     }

//     private void removeSelectedItem() {
//         CartItem selected = cartList.getSelectedValue();
//         if (selected != null) {
//             customer.removeFromCart(selected);
//             refreshCart();
//         } else {
//             JOptionPane.showMessageDialog(this, "Please select an item to remove.");
//         }
//     }

//     private void checkout() {
//         if (customer.getShoppingCart().getItems().isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Your cart is empty!");
//             return;
//         }

//         String street = JOptionPane.showInputDialog(this, "Street Address:");
//         String city = JOptionPane.showInputDialog(this, "City:");
//         String state = JOptionPane.showInputDialog(this, "State:");
//         String postal = JOptionPane.showInputDialog(this, "Postal Code:");

//         if (street != null && city != null && state != null && postal != null) {
//             ShippingAddress address = new ShippingAddress(street, city, state, postal);
//             Order order = customer.checkout(address);

//             if (order != null) {
//                 CreditCardPayment payment = new CreditCardPayment("PAY" + System.currentTimeMillis(), order.getTotalAmount());
//                 if (payment.processPayment()) {
//                     order.setPayment(payment);
//                     order.confirmOrder();
//                     JOptionPane.showMessageDialog(this, 
//                         "🎉 Order placed successfully!\n\nOrder ID: " + order.getOrderId() +
//                         "\nTotal: $" + order.getTotalAmount(), 
//                         "Success", JOptionPane.INFORMATION_MESSAGE);
//                     refreshCart();
//                 }
//             }
//         }
//     }

//     // ==================== Custom Renderer for Cart Items ====================
//     class CartItemRenderer extends DefaultListCellRenderer {
//         @Override
//         public Component getListCellRendererComponent(JList<?> list, Object value, 
//                 int index, boolean isSelected, boolean cellHasFocus) {
            
//             super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
//             if (value instanceof CartItem) {
//                 CartItem item = (CartItem) value;
//                 ProductVariant variant = item.getVariant();
                
//                 String displayText = String.format("%s %s (%s) × %d = $%.2f",
//                     variant.getColor(),
//                     variant.getSize(),
//                     variant.getVariantId(),
//                     item.getQuantity(),
//                     item.getSubTotal());

//                 setText(displayText);
//                 setFont(new Font("Arial", Font.PLAIN, 14));
//             }
//             return this;
//         }
//     }
// }

package view;

import model.*;
import controller.ECommerceController;

import javax.swing.*;
import java.awt.*;

public class CartPanel extends JPanel {

    private Customer customer;
    private ECommerceController controller;
    private MainCustomerFrame parentFrame;
    private DefaultListModel<CartItem> cartModel;
    private JList<CartItem> cartList;
    private JLabel totalLabel;

    public CartPanel(Customer customer, ECommerceController controller, MainCustomerFrame parent) {
        this.customer = customer;
        this.controller = controller;
        this.parentFrame = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("🛒 Shopping Cart"));

        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);
        cartList.setCellRenderer(new CartItemRenderer());

        JButton removeBtn = new JButton("Remove Item");
        JButton checkoutBtn = new JButton("Proceed to Checkout");
        checkoutBtn.setBackground(new Color(0, 150, 0));
        // checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 14));

        removeBtn.addActionListener(e -> removeSelectedItem());
        checkoutBtn.addActionListener(e -> checkout());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        bottomPanel.add(totalLabel);
        bottomPanel.add(removeBtn);
        bottomPanel.add(checkoutBtn);

        add(new JScrollPane(cartList), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshCart();
    }

    public void refreshCart() {
        cartModel.clear();
        for (CartItem item : customer.getShoppingCart().getItems()) {
            cartModel.addElement(item);
        }
        totalLabel.setText("Total: $" + String.format("%.2f", customer.getShoppingCart().calculateTotal()));
    }

    private void removeSelectedItem() {
        CartItem selected = cartList.getSelectedValue();
        if (selected != null) {
            customer.removeFromCart(selected);
            refreshCart();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
        }
    }

    private void checkout() {
        if (customer.getShoppingCart().getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!");
            return;
        }

        String street = JOptionPane.showInputDialog(this, "Street Address:");
        String city = JOptionPane.showInputDialog(this, "City:");
        String state = JOptionPane.showInputDialog(this, "State:");
        String postal = JOptionPane.showInputDialog(this, "Postal Code:");

        if (street != null && city != null && state != null && postal != null) {
            ShippingAddress address = new ShippingAddress(street, city, state, postal);
            Order order = customer.checkout(address);

            if (order != null) {
                CreditCardPayment payment = new CreditCardPayment("PAY" + System.currentTimeMillis(), order.getTotalAmount());
                if (payment.processPayment()) {
                    order.setPayment(payment);
                    order.confirmOrder();
                    JOptionPane.showMessageDialog(this, 
                        "🎉 Order placed successfully!\n\nOrder ID: " + order.getOrderId() +
                        "\nTotal: $" + String.format("%.2f", order.getTotalAmount()), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshCart();
                }
            }
        }
    }

    // ==================== IMPROVED Cart Item Renderer ====================
    class CartItemRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof CartItem) {
                CartItem item = (CartItem) value;
                ProductVariant variant = item.getVariant();
                
                String productName = "Fashion Item";
                
                // Find product name by searching in controller
                for (Product p : controller.getAllProducts()) {
                    if (p.getVariants().contains(variant)) {
                        productName = p.getName();
                        break;
                    }
                }

                String displayText = String.format("%s - %s %s × %d = $%.2f",
                    productName,
                    variant.getColor(),
                    variant.getSize(),
                    item.getQuantity(),
                    item.getSubTotal());

                setText(displayText);
                setFont(new Font("Arial", Font.PLAIN, 14));
            }
            return this;
        }
    }
}