package view;

import model.*;
import controller.ECommerceController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        setBorder(BorderFactory.createTitledBorder("Shopping Cart"));

        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);

        JButton removeBtn = new JButton("Remove Item");
        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.setBackground(new Color(0, 150, 0));
        // checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 14));

        removeBtn.addActionListener(e -> removeSelectedItem());
        checkoutBtn.addActionListener(e -> checkout());

        JPanel bottomPanel = new JPanel();
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
        }
    }

    private void checkout() {
        if (customer.getShoppingCart().getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }

        String street = JOptionPane.showInputDialog("Street Address:");
        String city = JOptionPane.showInputDialog("City:");
        String state = JOptionPane.showInputDialog("State:");
        String postal = JOptionPane.showInputDialog("Postal Code:");

        ShippingAddress address = new ShippingAddress(street, city, state, postal);
        Order order = customer.checkout(address);

        if (order != null) {
            // Process Payment
            CreditCardPayment payment = new CreditCardPayment("PAY" + System.currentTimeMillis(), order.getTotalAmount());
            if (payment.processPayment()) {
                order.setPayment(payment);
                order.confirmOrder();
                JOptionPane.showMessageDialog(this, "Order placed successfully!\nOrder ID: " + order.getOrderId());
                refreshCart();
            }
        }
    }
}