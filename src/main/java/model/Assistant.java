package model;

public class Assistant extends Account {
    public Assistant(String username, String password, String email, String phoneNumber, String firstName, String lastName, String profilePicPath) {
        super(username, password, email, phoneNumber, firstName, lastName, profilePicPath);
    }

    public Assistant(Account account) {
        this(account.getUsername(), account.getPassword(), account.getEmail(),
                account.getPhoneNumber(), account.getFirstName(),
                account.getLastName(), account.getProfilePicPath());
    }
}