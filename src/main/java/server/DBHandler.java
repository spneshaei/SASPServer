package server;

import com.google.gson.Gson;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DBHandler {
    public static void initializeProductTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
            System.out.println("Database Opened...");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String productSQL = "CREATE TABLE Products " +
                    "(p_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " productId varchar(50) NOT NULL, " +
                    " status varchar(500) NOT NULL, " +
                    " productName varchar(500) NOT NULL, " +
                    " brand varchar(500) NOT NULL, " +
                    " price int NOT NULL, " +
                    " discountPercent int NOT NULL, " +
                    " visitCount int NOT NULL, " +
                    " sellers varchar(2000) NOT NULL, " +
                    " numberAvailable int NOT NULL, " +
                    " category varchar(500) NOT NULL, " +
                    " description varchar(500) NOT NULL, " +
                    " imageURL varchar(500) NOT NULL, " +
                    " slides varchar(500) NOT NULL, " +
                    " comments varchar(2000) NOT NULL, " +
                    " scores varchar(2000) NOT NULL, " +
                    " dateCreated varchar(100) NOT NULL, " +
                    " currentSeller varchar(50) NOT NULL, " +
                    " isSelected varchar(10) NOT NULL, " +
                    " features varchar(2000) NOT NULL) ";
            stmt.executeUpdate(productSQL);
            String usersSQL = "CREATE TABLE Users " +
                    "(p_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " username varchar(100) NOT NULL, " +
                    " password varchar(100) NOT NULL, " +
                    " email varchar(100) NOT NULL, " +
                    " phoneNumber varchar(50) NOT NULL, " +
                    " firstName varchar(500) NOT NULL, " +
                    " lastName varchar(500) NOT NULL, " +
                    " profilePicPath varchar(500) NOT NULL, " +
                    " coupons varchar(2000) NOT NULL, " +
                    " logs varchar(2000) NOT NULL, " +
                    " cart varchar(5000), " +
                    " address varchar(2000), " +
                    " credit int NOT NULL, " +
                    " bankAccountNumber varchar(100) NOT NULL, " +
                    " isPermittedToSell varchar(10), " +
                    " companyDetails varchar(500)) ";
            stmt.executeUpdate(usersSQL);
            String logsSQL = "CREATE TABLE PurchaseLogs " +
                    "(p_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " logID varchar(50) NOT NULL, " +
                    " dateCreated varchar(100) NOT NULL, " +
                    " price int NOT NULL, " +
                    " discountAmount int NOT NULL, " +
                    " products varchar(2500) NOT NULL, " +
                    " customer varchar(100) NOT NULL, " +
                    " deliveryStatus varchar(100) NOT NULL) ";
            stmt.executeUpdate(logsSQL);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Database successfully created");
    }

    public static void insertProductsIntoTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
            System.out.println("Database Opened...");
            stmt = c.createStatement();
            c.setAutoCommit(false);
            String deleteSQL = "DELETE FROM Products";
            stmt.executeUpdate(deleteSQL);
            c.commit();
            stmt.close();
            c.close();
            Gson gson = new Gson();
            for (Product product : DataManager.shared().getAllProducts()) {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
                System.out.println("Database Opened...");
                stmt = c.createStatement();
                c.setAutoCommit(false);
                StringBuilder sellers = new StringBuilder();
                for (String s : product.getSellersAsStringArray()) {
                    if (!sellers.toString().equals("")) sellers.append(";;;;");
                    sellers.append(s);
                }
                StringBuilder slides = new StringBuilder();
                for (int s : product.getSlides()) {
                    if (!slides.toString().equals("")) slides.append(";;;;");
                    slides.append(s);
                }
                StringBuilder comments = new StringBuilder();
                for (Comment s : product.getComments()) {
                    if (!comments.toString().equals("")) comments.append(";;;;");
                    comments.append(gson.toJson(s));
                }
                StringBuilder scores = new StringBuilder();
                for (Score s : product.getScores()) {
                    if (!scores.toString().equals("")) scores.append(";;;;");
                    scores.append(gson.toJson(s));
                }
                String insertSQL = "INSERT INTO Products (productId, status, productName, brand, " +
                        "price, discountPercent, visitCount, sellers, numberAvailable, category, " +
                        "description, imageURL, slides, comments, scores, dateCreated, currentSeller, " +
                        "isSelected, features)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = c.prepareStatement(insertSQL);
                ps.setString(1, product.getProductId());
                ps.setString(2, product.getStatus().toString());
                ps.setString(3, product.getName());
                ps.setString(4, product.getBrand());
                ps.setInt(5, product.getPrice());
                ps.setInt(6, product.getDiscountPercent());
                ps.setInt(7, product.getVisitCount());
                ps.setString(8, sellers.toString());
                ps.setInt(9, product.getNumberAvailable());
                ps.setString(10, product.getCategory().getId());
                ps.setString(11, product.getDescription());
                ps.setString(12, product.getImageURL());
                ps.setString(13, slides.toString());
                ps.setString(14, comments.toString());
                ps.setString(15, scores.toString());
                ps.setString(16, product.getDateCreatedAsString());
                ps.setString(17, product.getCurrentSeller().getUsername());
                ps.setString(18, product.isSelected() ? "true" : "false");
                // TODO: Important: Does the next line work??
                ps.setString(19, gson.toJson(product.getFeatures()));
                ps.executeQuery();
                c.commit();
                stmt.close();
                c.close();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public static void insertUsersIntoTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
            System.out.println("Database Opened...");
            stmt = c.createStatement();
            c.setAutoCommit(false);
            Gson gson = new Gson();
            String deleteSQL = "DELETE FROM Users";
            stmt.executeUpdate(deleteSQL);
            c.commit();
            stmt.close();
            c.close();
            for (Account account : DataManager.shared().getAllAccounts()) {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
                System.out.println("Database Opened...");
                stmt = c.createStatement();
                c.setAutoCommit(false);
                StringBuilder coupons = new StringBuilder();
                for (Coupon s : account.getCoupons()) {
                    if (!coupons.toString().equals("")) coupons.append(";;;;");
                    coupons.append(gson.toJson(s));
                }
                StringBuilder logs = new StringBuilder();
                for (PurchaseLog s : account.getPurchaseLogs()) {
                    if (!logs.toString().equals("")) logs.append(";;;;");
                    logs.append(gson.toJson(s));
                }
                for (SellLog s : account.getSellLogs()) {
                    if (!logs.toString().equals("")) logs.append(";;;;");
                    logs.append(gson.toJson(s));
                }
                String insertSQL = "INSERT INTO Users (username, password, email, phoneNumber, " +
                        "firstName, lastName, profilePicPath, coupons, logs, cart, " +
                        "address, credit, bankAccountNumber, isPermittedToSell, companyDetails)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = c.prepareStatement(insertSQL);
                ps.setString(1, account.getUsername());
                ps.setString(2, account.getPassword());
                ps.setString(3, account.getEmail());
                ps.setString(4, account.getPhoneNumber());
                ps.setString(5, account.getFirstName());
                ps.setString(6, account.getLastName());
                ps.setString(7, account.getProfilePicPath());
                ps.setString(8, coupons.toString());
                ps.setString(9, logs.toString());
                // TODO: Does null in the next two lines work??
                ps.setString(10, (account instanceof Customer) ? gson.toJson(((Customer) account).getCart()) : null);
                ps.setString(11, (account instanceof Customer) ? ((Customer) account).getAddress() : null);
                ps.setInt(12, account.getCredit());
                ps.setString(13, account.getBankAccountNumber());
                ps.setString(14, (account instanceof Seller) ? (((Seller) account).isPermittedToSell() ? "true" : "false") : null);
                ps.setString(15, (account instanceof Seller) ? ((Seller) account).getCompanyDetails() : null);
                ps.executeQuery();
                c.commit();
                stmt.close();
                c.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public static void insertPurchaseLogsIntoTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
            System.out.println("Database Opened...");
            stmt = c.createStatement();
            c.setAutoCommit(false);
            String deleteSQL = "DELETE FROM PurchaseLogs";
            stmt.executeUpdate(deleteSQL);
            c.commit();
            stmt.close();
            c.close();
            for (PurchaseLog log : DataManager.shared().getPurchaseLogs()) {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
                System.out.println("Database Opened...");
                stmt = c.createStatement();
                c.setAutoCommit(false);
                String insertSQL = "INSERT INTO PurchaseLogs (logID, dateCreated, price, discountAmount, " +
                        "products, customer, deliveryStatus)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = c.prepareStatement(insertSQL);
                ps.setString(1, log.getId());
                ps.setString(2, log.getDateAsString());
                ps.setLong(3, log.getPrice());
                ps.setInt(4, log.getDiscountAmount());
                ps.setString(5, log.getProducts().toString());
                ps.setString(6, log.getCustomer().getUsername());
                ps.setString(7, log.getDeliveryStatus().toString());
                ps.executeQuery();
                c.commit();
                stmt.close();
                c.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public static void selectProductsFromTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
            // TODO: The next line??
//            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Products;");
            while (rs.next()) {
                String productId = rs.getString("productId");
                if (productId == null || productId.equals("")) continue;
                Product product = DataManager.shared().getProductWithId(productId);
                if (product == null) continue;
                product.setName(rs.getString("productName"));
                product.setBrand(rs.getString("brand"));
                product.setPrice(rs.getInt("price"));
                product.setDiscountPercent(rs.getInt("discountPercent"));
                product.setVisitCount(rs.getInt("visitCount"));
                product.setNumberAvailable(rs.getInt("numberAvailable"));
                product.setDescription(rs.getString("description"));
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // TODO: SQL is not yet working

    public static void selectUsersFromTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
            // TODO: The next line??
//            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users;");
            while (rs.next()) {
                String username = rs.getString("username");
                if (username == null || username.equals("")) continue;
                Account account = DataManager.shared().getAccountWithGivenUsername(username);
                if (account == null) continue;
                account.setPassword(rs.getString("password"));
                account.setEmail(rs.getString("email"));
                account.setPhoneNumber(rs.getString("phoneNumber"));
                account.setFirstName(rs.getString("firstName"));
                account.setLastName(rs.getString("lastName"));
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void selectPurchaseLogsFromTable() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SqliteJavaDB.db");
            // TODO: The next line??
//            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PurchaseLogs;");
            while (rs.next()) {
                String id = rs.getString("logID");
                if (id == null || id.equals("")) continue;
                PurchaseLog purchaseLog = DataManager.shared().getPurchaseLogWithID(id);
                if (purchaseLog == null) continue;
                purchaseLog.setPrice(rs.getInt("price"));
                purchaseLog.setDiscountAmount(rs.getInt("discountAmount"));
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // TODO: Only some parameters in each selection...

}
