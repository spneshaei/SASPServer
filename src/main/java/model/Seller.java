package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Seller extends Account {
    private String companyDetails;
    private boolean isPermittedToSell;
    private String hostAddress;
    private int port;

    public Seller(String username, String password, String email, String phone, String firstName,
                  String lastName, String companyDetails, String profilePicPath) {
        super(username, password, email, phone, firstName, lastName, profilePicPath);
        this.companyDetails = companyDetails;
        isPermittedToSell = false;
    }

    public Seller(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(),
                account.getPhoneNumber(), account.getFirstName(), account.getLastName(),
                "", account.getProfilePicPath());
    }

    public Seller(Seller seller) {
        this(seller.getUsername(), seller.getPassword(), seller.getEmail(),
                seller.getPhoneNumber(), seller.getFirstName(), seller.getLastName(),
                seller.getCompanyDetails(), seller.getCompanyDetails());
        isPermittedToSell = false;
    }

    public boolean isPermittedToSell() {
        return isPermittedToSell;
    }

    public void setPermittedToSell(boolean permittedToSell) {
        isPermittedToSell = permittedToSell;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public ArrayList<Product> getProducts() {
        return DataManager.shared().getAllProducts().stream().filter(product ->
                product.getSellers().contains(this))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Sale> getSales() {
        return DataManager.shared().getAllSales().stream().filter(sale ->
                sale.getSeller().equals(this)).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String toString() {
        return super.toString() + "\n\n" +
                "Company Details: " + companyDetails;
    }
}
