package model;

import java.util.HashMap;

public class Customer extends Account {
    // TODO: Does containing cart make error for Gson??
    private Cart cart;
    private String address = "";

    public Customer(String username, String password, String email, String phone,
                    String firstName, String lastName, String profilePicPath) {
        this(username, password, email, phone, firstName, lastName, new Cart(), profilePicPath);
    }

    public Customer(String username, String password, String email, String phoneNumber,
                    String firstName, String lastName, Cart cart, String profilePicPath) {
        super(username, password, email, phoneNumber, firstName, lastName, profilePicPath);
        this.cart = cart;
    }

    public Customer(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(),
                account.getPhoneNumber(), account.getFirstName(), account.getLastName(),
                account.getProfilePicPath());
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        DataManager.saveData();
    }

    public Cart getCart() {
        return cart;
    }

    public void emptyCart() {
        cart.setProducts(new HashMap<>());
    }

    public void addProductToCart(Product product) {
        cart.addProduct(product);
        DataManager.saveData();
    }

    public boolean hasPurchasedProduct(Product product) {
        return DataManager.shared().getAllLogs().stream()
                .anyMatch(log -> log instanceof PurchaseLog
                        && ((PurchaseLog) log).getCustomer().getUsername().equals(this.getUsername())
                        && log.getProducts().containsKey(product));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer))
            return false;
        return super.equals(obj);
    }
}
