// package view;

// import model.*;
// import controller.ECommerceController;

// import javax.swing.*;
// import java.awt.*;
// import java.util.List;

// public class UserManagementDialog extends JDialog {

//     private ECommerceController controller;
//     private DefaultListModel<User> userModel;
//     private JList<User> userList;

//     public UserManagementDialog(JFrame parent, ECommerceController controller) {
//         super(parent, "Manage Users - CRUD", true);
//         this.controller = controller;
//         initUI();
//         loadUsers();
//     }

//     private void initUI() {
//         setSize(700, 500);
//         setLocationRelativeTo(null);
//         setLayout(new BorderLayout());

//         userModel = new DefaultListModel<>();
//         userList = new JList<>(userModel);

//         JButton addBtn = new JButton("Add User");
//         JButton editBtn = new JButton("Edit User");
//         JButton deleteBtn = new JButton("Delete User");
//         JButton refreshBtn = new JButton("Refresh");

//         addBtn.addActionListener(e -> addUser());
//         editBtn.addActionListener(e -> editUser());
//         deleteBtn.addActionListener(e -> deleteUser());
//         refreshBtn.addActionListener(e -> loadUsers());

//         JPanel buttonPanel = new JPanel();
//         buttonPanel.add(addBtn);
//         buttonPanel.add(editBtn);
//         buttonPanel.add(deleteBtn);
//         buttonPanel.add(refreshBtn);

//         add(new JScrollPane(userList), BorderLayout.CENTER);
//         add(buttonPanel, BorderLayout.SOUTH);
//     }

//     private void loadUsers() {
//         userModel.clear();
//         for (User user : controller.getAllUsers()) {
//             userModel.addElement(user);
//         }
//     }

//     private void addUser() {
//         // Simple form for demo
//         String name = JOptionPane.showInputDialog(this, "Enter Name:");
//         String email = JOptionPane.showInputDialog(this, "Enter Email:");
//         String password = JOptionPane.showInputDialog(this, "Enter Password:");
//         String type = (String) JOptionPane.showInputDialog(this, "User Type:", "Select Type",
//                 JOptionPane.QUESTION_MESSAGE, null, new String[]{"Customer", "Seller"}, "Customer");

//         if (name != null && email != null && password != null) {
//             User newUser;
//             String id = type.substring(0,1).toUpperCase() + System.currentTimeMillis();
//             if (type.equals("Customer")) {
//                 newUser = new Customer(id, name, email, password);
//             } else {
//                 newUser = new Seller(id, name, email, password);
//             }
//             controller.addUser(newUser);
//             loadUsers();
//             JOptionPane.showMessageDialog(this, "User added successfully!");
//         }
//     }

//     // private void editUser() {
//     //     User selected = userList.getSelectedValue();
//     //     if (selected == null) {
//     //         JOptionPane.showMessageDialog(this, "Please select a user to edit.");
//     //         return;
//     //     }
//     //     JOptionPane.showMessageDialog(this, "Edit functionality for " + selected.getName() + " (Demo - Full edit can be extended)");
//     //     // Can be extended with full form
//     // }
//     private void editUser() {
//         User selected = userList.getSelectedValue();
//         if (selected == null) {
//             JOptionPane.showMessageDialog(this, "Please select a user to edit.");
//             return;
//         }

//         // Create editable fields
//         JTextField nameField = new JTextField(selected.getName());
//         JTextField emailField = new JTextField(selected.getEmail());
//         JPasswordField passwordField = new JPasswordField(selected.getPassword());

//         Object[] message = {
//             "User ID: " + selected.getUserId(),
//             "Name:", nameField,
//             "Email:", emailField,
//             "Password:", passwordField
//         };

//         int option = JOptionPane.showConfirmDialog(this, message, 
//                 "Edit User - " + selected.getName(), JOptionPane.OK_CANCEL_OPTION);

//         if (option == JOptionPane.OK_OPTION) {
//             try {
//                 String newName = nameField.getText().trim();
//                 String newEmail = emailField.getText().trim();
//                 String newPassword = new String(passwordField.getPassword()).trim();

//                 if (newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
//                     JOptionPane.showMessageDialog(this, "All fields are required!");
//                     return;
//                 }

//                 // Update user details using reflection or setters (since we don't have setters for all, we'll recreate)
//                 User updatedUser;
//                 if (selected instanceof Customer) {
//                     updatedUser = new Customer(selected.getUserId(), newName, newEmail, newPassword);
//                 } else if (selected instanceof Seller) {
//                     updatedUser = new Seller(selected.getUserId(), newName, newEmail, newPassword);
//                 } else {
//                     updatedUser = new Admin(selected.getUserId(), newName, newEmail, newPassword);
//                 }

//                 // Replace in controller
//                 controller.removeUser(selected);
//                 controller.addUser(updatedUser);

//                 loadUsers();
//                 JOptionPane.showMessageDialog(this, "User updated successfully!");

//             } catch (Exception ex) {
//                 JOptionPane.showMessageDialog(this, "Error updating user.");
//             }
//         }
//     }

//     private void deleteUser() {
//         User selected = userList.getSelectedValue();
//         if (selected == null) return;

//         int confirm = JOptionPane.showConfirmDialog(this, 
//             "Delete user: " + selected.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
//         if (confirm == JOptionPane.YES_OPTION) {
//             controller.removeUser(selected);
//             loadUsers();
//             JOptionPane.showMessageDialog(this, "User deleted.");
//         }
//     }
// }

package view;

import model.*;
import controller.ECommerceController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserManagementDialog extends JDialog {

    private ECommerceController controller;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserManagementDialog(JFrame parent, ECommerceController controller) {
        super(parent, "Manage Users - CRUD", true);
        this.controller = controller;
        initUI();
        loadUsers();
    }

    private void initUI() {
        setSize(850, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Table Setup
        String[] columnNames = {"User ID", "Name", "Email", "User Type"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setRowHeight(25);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("➕ Add User");
        JButton editBtn = new JButton("✏️ Edit User");
        JButton deleteBtn = new JButton("🗑️ Delete User");
        JButton refreshBtn = new JButton("🔄 Refresh");

        addBtn.addActionListener(e -> addUser());
        editBtn.addActionListener(e -> editUser());
        deleteBtn.addActionListener(e -> deleteUser());
        refreshBtn.addActionListener(e -> loadUsers());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        add(new JScrollPane(userTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadUsers() {
        tableModel.setRowCount(0); // Clear table

        for (User user : controller.getAllUsers()) {
            String userType = "Unknown";
            if (user instanceof Customer) userType = "Customer";
            else if (user instanceof Admin) userType = "Admin";
            else if (user instanceof Seller) userType = "Seller";

            tableModel.addRow(new Object[]{
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                userType
            });
        }
    }

    private void addUser() {
        String name = JOptionPane.showInputDialog(this, "Enter Full Name:");
        String email = JOptionPane.showInputDialog(this, "Enter Email:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        String type = (String) JOptionPane.showInputDialog(this, "Select User Type:", "User Type",
                JOptionPane.QUESTION_MESSAGE, null, 
                new String[]{"Customer", "Seller"}, "Customer");

        if (name != null && email != null && password != null && type != null) {
            String id = (type.equals("Customer") ? "C" : "S") + System.currentTimeMillis();
            
            User newUser = type.equals("Customer") 
                ? new Customer(id, name, email, password) 
                : new Seller(id, name, email, password);

            controller.addUser(newUser);
            loadUsers();
            JOptionPane.showMessageDialog(this, "✅ User added successfully!");
        }
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.");
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        User selected = findUserById(userId);
        if (selected == null) return;

        JTextField nameField = new JTextField(selected.getName());
        JTextField emailField = new JTextField(selected.getEmail());
        JPasswordField passwordField = new JPasswordField(selected.getPassword());

        Object[] message = {
            "User ID: " + selected.getUserId(),
            "Name:", nameField,
            "Email:", emailField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, 
                "Edit User", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String newName = nameField.getText().trim();
                String newEmail = emailField.getText().trim();
                String newPassword = new String(passwordField.getPassword()).trim();

                if (newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }

                User updatedUser;
                if (selected instanceof Customer) {
                    updatedUser = new Customer(selected.getUserId(), newName, newEmail, newPassword);
                } else if (selected instanceof Seller) {
                    updatedUser = new Seller(selected.getUserId(), newName, newEmail, newPassword);
                } else {
                    updatedUser = new Admin(selected.getUserId(), newName, newEmail, newPassword);
                }

                controller.removeUser(selected);
                controller.addUser(updatedUser);

                loadUsers();
                JOptionPane.showMessageDialog(this, "✅ User updated successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error updating user.");
            }
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user.");
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        User selected = findUserById(userId);
        if (selected == null) return;

        if (selected instanceof Admin) {
            JOptionPane.showMessageDialog(this, "Cannot delete Admin user for security reasons.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete user: " + selected.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.removeUser(selected);
            loadUsers();
            JOptionPane.showMessageDialog(this, "🗑️ User deleted successfully.");
        }
    }

    private User findUserById(String userId) {
        for (User user : controller.getAllUsers()) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}