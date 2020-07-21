package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DataManager {
    private static DataManager sharedInstance;
    private HashMap<String, String> loggedInAccountsAndTokens = new HashMap<>();
    private ArrayList<Customer> allCustomers = new ArrayList<>();
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<Administrator> allAdministrators = new ArrayList<>();
    private ArrayList<Assistant> allAssistants = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Coupon> allCoupons = new ArrayList<>();
    private ArrayList<AddProductBySellerRequest> addProductBySellerRequests = new ArrayList<>();
    private ArrayList<AddSaleBySellerRequest> addSaleBySellerRequests = new ArrayList<>();
    private ArrayList<EditProductBySellerRequest> editProductBySellerRequests = new ArrayList<>();
    private ArrayList<EditSaleBySellerRequest> editSaleBySellerRequests = new ArrayList<>();
    private ArrayList<SellerRegistrationRequest> sellerRegistrationRequests = new ArrayList<>();
    private ArrayList<Ad> allAds = new ArrayList<>();
    private ArrayList<AddAdBySellerRequest> adRequests = new ArrayList<>();
    private ArrayList<Auction> auctions = new ArrayList<>();
    private ArrayList<AssistantMessage> allAssistantMessages = new ArrayList<>();
    private String adminBankAccountNumber = "";
    private int mimimumCredit = 0;
    private int karmozd = 0;

    private transient ArrayList<IPRecord> ipRecords = new ArrayList<>();
    private transient ArrayList<IPRecord> unsuccessfulLoginIPRecords = new ArrayList<>();
    public static transient ArrayList<String> commonPasswords = new ArrayList<>();

    public ArrayList<AssistantMessage> getAllAssistantMessages() {
        return allAssistantMessages;
    }

    public void setAllAssistantMessages(ArrayList<AssistantMessage> allAssistantMessages) {
        this.allAssistantMessages = allAssistantMessages;
    }

    public ArrayList<Assistant> getAllAssistants() {
        return allAssistants;
    }

    public void setAllAssistants(ArrayList<Assistant> allAssistants) {
        this.allAssistants = allAssistants;
    }

    public int getMimimumCredit() {
        return mimimumCredit;
    }

    public void setMimimumCredit(int mimimumCredit) {
        this.mimimumCredit = mimimumCredit;
        DataManager.saveData();
    }

    public int getKarmozd() {
        return mimimumCredit;
    }

    public void setKarmozd(int karmozd) {
        this.karmozd = karmozd;
        DataManager.saveData();
    }

    // TODO: Does the next method work correctly??

    public boolean hasMoreThanAThousandIPRecordsInASecond(IPRecord currentIPRecord) {
        synchronized (ipRecords) {
//            ArrayList<IPRecord> ipRecords = this.ipRecords;
            long count = 0L;
            for (Iterator<IPRecord> iterator = ipRecords.iterator(); iterator.hasNext(); ) {
                IPRecord ipRecord = iterator.next();
                if (ipRecord.getIp().equals(currentIPRecord.getIp()) &&
                        Math.abs(ChronoUnit.SECONDS.between(ipRecord.getDate(), currentIPRecord.getDate())) < 2) {
                    count += 1;
                }
            }
            return (int) count > 1000;
        }
    }

    public boolean hasMoreThanTenUnsuccessfulLoginIPRecordsIn100Seconds(IPRecord currentIPRecord) {
        ArrayList<IPRecord> unsuccessfulLoginIPRecords = this.unsuccessfulLoginIPRecords;
        return (int) unsuccessfulLoginIPRecords.stream().filter(ipRecord -> ipRecord.getIp().equals(currentIPRecord.getIp()) &&
                Math.abs(ChronoUnit.SECONDS.between(ipRecord.getDate(), currentIPRecord.getDate())) < 100).count() > 10;
    }

    public void addIPRecord(IPRecord ipRecord) {
        ipRecords.add(ipRecord);
    }

    public void addUnsuccessfulLoginIPRecord(IPRecord ipRecord) {
        unsuccessfulLoginIPRecords.add(ipRecord);
    }

    public boolean isMadeAdminBankAccount() {
        return !adminBankAccountNumber.equals("");
    }

    public ArrayList<Auction> getAuctions() {
        return auctions;
    }

    public Auction getAuctionWithId(String id) {
        return auctions.stream().filter(auction -> auction.getId().equals(id)).findFirst().orElse(null);
    }

    public HashMap<String, String> getLoggedInAccountsAndTokens() {
        return loggedInAccountsAndTokens;
    }

    public String getAdminBankAccountNumber() {
        return adminBankAccountNumber;
    }

    public void setAdminBankAccountNumber(String adminBankAccountNumber) {
        this.adminBankAccountNumber = adminBankAccountNumber;
    }

    private ArrayList<Category> allCategories = new ArrayList<>();
    private ArrayList<Sale> allSales = new ArrayList<>();
    private ArrayList<PurchaseLog> purchaseLogs = new ArrayList<>();
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    private Cart temporaryCart = new Cart();

    private DataManager() {
    }

    public void setAllCustomers(ArrayList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public void setAllSellers(ArrayList<Seller> allSellers) {
        this.allSellers = allSellers;
    }

    public void setAllAdministrators(ArrayList<Administrator> allAdministrators) {
        this.allAdministrators = allAdministrators;
    }

    public void setAllCategories(ArrayList<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public void setPurchaseLogs(ArrayList<PurchaseLog> purchaseLogs) {
        this.purchaseLogs = purchaseLogs;
    }


    public void setSellLogs(ArrayList<SellLog> sellLogs) {
        this.sellLogs = sellLogs;
    }

    public void setAllSales(ArrayList<Sale> allSales) {
        this.allSales = allSales;
    }

    public void setAllCoupons(ArrayList<Coupon> allCoupons) {
        this.allCoupons = allCoupons;
    }

    public static String getNewId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static DataManager shared() {
        if (sharedInstance == null) {
            sharedInstance = new DataManager();
        }
        return sharedInstance;
    }

    public void setAuctions(ArrayList<Auction> auctions) {
        this.auctions = auctions;
    }

    public static void saveData() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());

        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String json = gson.toJson(sharedInstance);
        try (PrintStream out = new PrintStream(new FileOutputStream("data.txt"))) {
            out.print(json);
        } catch (IOException e) {
            System.out.println("Unexpected exception happened in saving data: " + e.getLocalizedMessage());
        }

//        DBHandler.insertProductsIntoTable();
//        DBHandler.insertPurchaseLogsIntoTable();
//        DBHandler.insertUsersIntoTable();
    }

    // TODO: Not tested + what will happen in the client if we pass a common password??

    public static void loadData() {
        try {
            if (commonPasswords.isEmpty()) {
                Scanner sc = new Scanner(new FileInputStream("commonpasswords.txt"));
                while (sc.hasNextLine()) commonPasswords.add(sc.nextLine());
                sc.close();
            }
            String json = new String(Files.readAllBytes(Paths.get("data.txt")));
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());

            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());

            Gson gson = gsonBuilder.setPrettyPrinting().create();
            sharedInstance = gson.fromJson(json, DataManager.class);
        } catch (Exception e) {
            System.out.println("Unexpected exception happened in loading data: " + e.getLocalizedMessage());
//            e.printStackTrace();
        }

//        DBHandler.selectProductsFromTable();
//        DBHandler.selectUsersFromTable();
//        DBHandler.selectPurchaseLogsFromTable();
    }

    public ArrayList<Ad> getAllAds() {
        return allAds;
    }

    public ArrayList<Log> getAllLogs() {
        ArrayList<Log> result = new ArrayList<>();
        result.addAll(purchaseLogs);
        result.addAll(sellLogs);
        return result;
    }

    public ArrayList<PurchaseLog> getPurchaseLogs() {
        return purchaseLogs;
    }

    public void logout(String token) {
        loggedInAccountsAndTokens.remove(token);
        DataManager.saveData();
    }

    public void addAd(Ad ad) {
        allAds.add(ad);
        saveData();
    }

    public PurchaseLog purchaseLogForCustomerById(Customer customer, String id) {
        return (PurchaseLog) getAllLogs().stream()
                .filter(log -> log instanceof PurchaseLog && log.getId().equals(id)
                        && ((PurchaseLog) log).getCustomer().getUsername().equals(customer.getUsername()))
                .findFirst().orElse(null);
    }

    public Cart getTemporaryCart() {
        return temporaryCart;
    }

    public void setTemporaryCart(Cart temporaryCart) {
        this.temporaryCart = temporaryCart;
        saveData();
    }

    public ArrayList<Sale> getAllSales() {
        return allSales;
    }

    public ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public ArrayList<Seller> getAllSellers() {
        return allSellers;
    }

    public ArrayList<Administrator> getAllAdministrators() {
        return allAdministrators;
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> result = new ArrayList<>();
        result.addAll(allCustomers);
        result.addAll(allSellers);
        result.addAll(allAdministrators);
        result.addAll(allAssistants);
        return result;
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public ArrayList<Request> getAllRequests() {
        ArrayList<Request> result = new ArrayList<>();
        result.addAll(addProductBySellerRequests);
        result.addAll(addSaleBySellerRequests);
        result.addAll(editProductBySellerRequests);
        result.addAll(editSaleBySellerRequests);
        result.addAll(sellerRegistrationRequests);
        result.addAll(adRequests);
        return result;
    }

    public ArrayList<AddProductBySellerRequest> getAddProductBySellerRequests() {
        return addProductBySellerRequests;
    }

    public ArrayList<AddSaleBySellerRequest> getAddSaleBySellerRequests() {
        return addSaleBySellerRequests;
    }

    public ArrayList<EditProductBySellerRequest> getEditProductBySellerRequests() {
        return editProductBySellerRequests;
    }

    public ArrayList<EditSaleBySellerRequest> getEditSaleBySellerRequests() {
        return editSaleBySellerRequests;
    }

    public ArrayList<SellerRegistrationRequest> getSellerRegistrationRequests() {
        return sellerRegistrationRequests;
    }

    public ArrayList<AddAdBySellerRequest> getAdRequests() {
        return adRequests;
    }

    public void addRequest(Request request) {
        if (request instanceof AddProductBySellerRequest) {
            addProductBySellerRequests.add((AddProductBySellerRequest) request);
        } else if (request instanceof AddSaleBySellerRequest) {
            addSaleBySellerRequests.add((AddSaleBySellerRequest) request);
        } else if (request instanceof EditProductBySellerRequest) {
            editProductBySellerRequests.add((EditProductBySellerRequest) request);
        } else if (request instanceof EditSaleBySellerRequest) {
            editSaleBySellerRequests.add((EditSaleBySellerRequest) request);
        } else if (request instanceof SellerRegistrationRequest) {
            sellerRegistrationRequests.add((SellerRegistrationRequest) request);
        } else if (request instanceof AddAdBySellerRequest) {
            adRequests.add((AddAdBySellerRequest) request);
        }
        saveData();
    }

    public Request getRequestWithID(String id) {
        for (Request request : getAllRequests()) {
            if (request.getId().equals(id)) return request;
        }
        return null;
    }

    public Log getLogWithId(String id) {
        for (Log log : getAllLogs()) {
            if (log.getId().equals(id)) return log;
        }
        return null;
    }

    public Account getAccountWithToken(String token) {
        return DataManager.shared().getAccountWithGivenUsername(loggedInAccountsAndTokens.get(token));
    }

    public ArrayList<Coupon> getAllCoupons() {
        return allCoupons;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void removeProduct(String productID) {
        for (Product product : allProducts) {
            if (product.getProductId().equals(productID)) {
                allProducts.remove(product);
                saveData();
                return;
            }
        }
    }

    public Account getAccountWithGivenUsername(String username) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) return account;
        }
        return null;
    }

    public PurchaseLog getPurchaseLogWithID(String id) {
        for (PurchaseLog log : purchaseLogs) {
            if (log.getId().equals(id)) return log;
        }
        return null;
    }

    public String login(String username, String password) {
        String token = DataManager.getNewId();
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                loggedInAccountsAndTokens.put(token, account.getUsername());
                saveData();
                if (account instanceof Customer || account instanceof Administrator ||
                        account instanceof Seller || account instanceof Assistant) return token;
                break;
            }
        }
        saveData();
        return "";
    }

    public boolean hasAnyAdminRegistered() {
        for (Account account : getAllAccounts()) {
            if (account instanceof Administrator) return true;
        }
        return false;
    }

    public boolean doesUserWithGivenUsernameExist(String username) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void registerAccount(Account account) {
        if (account instanceof Customer) {
            allCustomers.add((Customer) account);
        } else if (account instanceof Seller) {
            allSellers.add((Seller) account);
        } else if (account instanceof Administrator) {
            allAdministrators.add((Administrator) account);
        } else if (account instanceof Assistant) {
            allAssistants.add((Assistant) account);
        }
        saveData();
    }

    public boolean givenUsernameHasGivenPassword(String username, String password) {
        for (Account account : getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return account.getPassword().equalsIgnoreCase(password);
            }
        }
        return false;
    }

    public void addLog(Log log) {
        if (log instanceof SellLog) {
            sellLogs.add((SellLog) log);
        } else if (log instanceof PurchaseLog) {
            purchaseLogs.add((PurchaseLog) log);
        }
        saveData();
    }

    public void addSale(Sale sale) {
        allSales.add(sale);
        saveData();
    }

    public void addProduct(Product product) {
        if (getProductWithId(product.getProductId()) != null) return;
        allProducts.add(product);
        saveData();
    }

    public void addCoupon(Coupon coupon) {
        allCoupons.add(coupon);
        saveData();
    }

    public void addCategory(Category category) {
        allCategories.add(category);
        saveData();
    }

    public void removeCoupon(String couponID) {
        allCoupons.stream().filter(coupon -> coupon.getId().equals(couponID))
                .findFirst().ifPresent(coupon -> allCoupons.remove(coupon));
        saveData();
    }

    public void removeRequest(String requestID) {
        addProductBySellerRequests.stream().filter(request -> request.getId().equals(requestID))
                .findFirst().ifPresent(request -> addProductBySellerRequests.remove(request));
        addSaleBySellerRequests.stream().filter(request -> request.getId().equals(requestID))
                .findFirst().ifPresent(request -> addSaleBySellerRequests.remove(request));
        editProductBySellerRequests.stream().filter(request -> request.getId().equals(requestID))
                .findFirst().ifPresent(request -> editProductBySellerRequests.remove(request));
        editSaleBySellerRequests.stream().filter(request -> request.getId().equals(requestID))
                .findFirst().ifPresent(request -> editSaleBySellerRequests.remove(request));
        sellerRegistrationRequests.stream().filter(request -> request.getId().equals(requestID))
                .findFirst().ifPresent(request -> sellerRegistrationRequests.remove(request));
        adRequests.stream().filter(request -> request.getId().equals(requestID))
                .findFirst().ifPresent(request -> adRequests.remove(request));
        saveData();
    }

    public void removeSale(String saleID) {
        allSales.stream().filter(sale -> sale.getOffId().equals(saleID))
                .findFirst().ifPresent(sale -> allSales.remove(sale));
        saveData();
    }

    public void removeAccount(String username) {
        allCustomers.stream().filter(account -> account.getUsername().equals(username))
                .findFirst().ifPresent(account -> allCustomers.remove(account));
        allSellers.stream().filter(account -> account.getUsername().equals(username))
                .findFirst().ifPresent(account -> allSellers.remove(account));
        allAdministrators.stream().filter(account -> account.getUsername().equals(username))
                .findFirst().ifPresent(account -> allAdministrators.remove(account));
        allAssistants.stream().filter(account -> account.getUsername().equals(username))
                .findFirst().ifPresent(account -> allAssistants.remove(account));
        saveData();
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void removeCategory(String categoryID) {
        allCategories.removeIf(c -> c.getId().equals(categoryID));
        saveData();
    }

    public Coupon getCouponWithId(String id) {
        return allCoupons.stream().filter(coupon -> coupon.getId().equals(id)).findFirst().orElse(null);
    }

    public Category getCategoryWithId(String id) {
        return allCategories.stream().filter(category -> category.getId().equals(id)).findFirst().orElse(null);
    }

    public Category getCategoryWithName(String name) {
        return allCategories.stream().filter(category -> category.getName().equals(name)).findFirst().orElse(null);
    }

    public Product getProductWithId(String id) {
        return allProducts.stream().filter(product -> product.getProductId().equals(id)).findFirst().orElse(null);
    }

    public Ad getAdWithId(String id) {
        return allAds.stream().filter(ad -> ad.getId().equals(id)).findFirst().orElse(null);
    }

    public Product getProductWithName(String name) {
        return allProducts.stream().filter(product -> product.getName().equals(name)).findFirst().orElse(null);
    }

    public Sale getSaleWithId(String id) {
        return allSales.stream().filter(sale -> sale.getOffId().equals(id)).findFirst().orElse(null);
    }

    public enum AccountType {
        CUSTOMER, ADMINISTRATOR, SELLER, NONE
    }

    public static LocalDateTime dateFromString(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu-HH-mm-ss");
        return LocalDateTime.parse(string, formatter);
    }

    public static String stringFromDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu-HH-mm-ss");
        return dateTime.format(formatter);
    }

}

class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu-HH-mm-ss");

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
//        if (localDateTime == null) return new JsonPrimitive("");
//        try {
        return new JsonPrimitive(formatter.format(localDateTime));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new JsonPrimitive("");
    }
}

class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("d-MMM-uuuu-HH-mm-ss").withLocale(Locale.ENGLISH));
    }
}

class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDate.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("d-MMM-yyyy").withLocale(Locale.ENGLISH));
    }
}

class LocalDateSerializer implements JsonSerializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate));
    }
}