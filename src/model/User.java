package model;

public abstract class User {
    private String userId;
    private String name;
    private String email;
    private String password;

    public User(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean login(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    public void logout() {
        System.out.println(name + " has logged out.");
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}