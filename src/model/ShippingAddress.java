package model;

public class ShippingAddress {
    private String street;
    private String city;
    private String state;
    private String postalCode;

    public ShippingAddress(String street, String city, String state, String postalCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    // Getters
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }

    @Override
    public String toString() {
        return street + ", " + city + ", " + state + " " + postalCode;
    }
}