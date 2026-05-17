package view;

import model.*;
import controller.ECommerceController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductManagementDialog extends JDialog {

    private ECommerceController controller;
    private DefaultListModel<Product> productModel;
    private JList<Product> productList;

    public ProductManagementDialog(JFrame parent, ECommerceController controller) {
        super(parent, "Manage Products - CRUD", true);
        this.controller = controller;
        initUI();
        loadProducts();
    }

    private void initUI() {
        setSize(850, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Product List
        productModel = new DefaultListModel<>();
        productList = new JList<>(productModel);
        productList.setCellRenderer(new ProductListRenderer());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("➕ Add Product");
        JButton editBtn = new JButton("✏️ Edit Product");
        JButton deleteBtn = new JButton("🗑️ Delete Product");
        JButton refreshBtn = new JButton("🔄 Refresh");

        addBtn.addActionListener(e -> addProduct());
        editBtn.addActionListener(e -> editProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
        refreshBtn.addActionListener(e -> loadProducts());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(new JScrollPane(productList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() {
        productModel.clear();
        for (Product p : controller.getAllProducts()) {
            productModel.addElement(p);
        }
    }

    private void addProduct() {
        JTextField nameField = new JTextField(20);
        JTextField descField = new JTextField(20);
        JTextField priceField = new JTextField(10);

        Object[] message = {
            "Product Name:", nameField,
            "Description:", descField,
            "Base Price ($):", priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Product", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String desc = descField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product name cannot be empty!");
                    return;
                }

                Product newProduct = new Product("P" + System.currentTimeMillis(), name, desc, price);
                
                // Add default variant
                ProductVariant defaultVariant = new ProductVariant(
                    "V" + System.currentTimeMillis(), 
                    "M", "Black", price, 50
                );
                newProduct.addVariant(defaultVariant);

                controller.addProduct(newProduct);
                loadProducts();
                JOptionPane.showMessageDialog(this, "✅ Product added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input! Please check price.");
            }
        }
    }

    private void editProduct() {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a product to edit.");
            return;
        }

        JTextField nameField = new JTextField(selected.getName());
        JTextField descField = new JTextField(selected.getDescription());
        JTextField priceField = new JTextField(String.valueOf(selected.getBasePrice()));

        Object[] message = {
            "Product Name:", nameField,
            "Description:", descField,
            "Base Price ($):", priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Product", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String newName = nameField.getText().trim();
                String newDesc = descField.getText().trim();
                double newPrice = Double.parseDouble(priceField.getText().trim());

                Product updatedProduct = new Product(selected.getProductId(), newName, newDesc, newPrice);
                
                // Copy existing variants
                for (ProductVariant v : selected.getVariants()) {
                    updatedProduct.addVariant(v);
                }

                controller.updateProduct(selected, updatedProduct);
                loadProducts();
                JOptionPane.showMessageDialog(this, "✅ Product updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input!");
            }
        }
    }

    private void deleteProduct() {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete product: " + selected.getName() + "?\nThis action cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.removeProduct(selected);
            loadProducts();
            JOptionPane.showMessageDialog(this, "🗑️ Product deleted successfully.");
        }
    }

    // Renderer for better display
    class ProductListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Product) {
                Product p = (Product) value;
                String text = String.format("%s - $%.2f (%d variants)", 
                    p.getName(), p.getBasePrice(), p.getVariants().size());
                setText(text);
                setFont(new Font("Arial", Font.PLAIN, 14));
            }
            return this;
        }
    }
}