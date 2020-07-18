package model;

public class Administrator extends Account {

    public Administrator(String username, String password, String email, String phone,
                         String firstName, String lastName, String profilePicPath) {
        super(username, password, email, phone, firstName, lastName, profilePicPath);
    }

    public Administrator(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(),
                account.getPhoneNumber(), account.getFirstName(),
                account.getLastName(), account.getProfilePicPath());
    }
}
