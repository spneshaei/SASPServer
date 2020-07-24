package server;

import com.google.gson.Gson;
import model.*;
import org.restlet.data.ClientInfo;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

// TODO: IMPORTANT: KARMOZD!!

// TODO: DDoS Not Tested!

public class MainResource extends ServerResource {
    @Get
    public Representation getAction() {
        IPRecord ipRecord = new IPRecord(getIP(), LocalDateTime.now());
        DataManager.shared().addIPRecord(ipRecord);
        if (DataManager.shared().hasMoreThanAThousandIPRecordsInASecond(ipRecord))
            return new StringRepresentation("too-many-times");
        String action = getQuery().getValues("action");
        if (action == null) return new StringRepresentation("wrong-action");
        String timeString = getQuery().getValues("time");
        if (timeString == null) return new StringRepresentation("wrong-time");
        LocalDateTime sentDate = DataManager.dateFromString(timeString);
        if (Math.abs(ChronoUnit.MINUTES.between(LocalDateTime.now(), sentDate)) >= 30)
            return new StringRepresentation("too-long-time");
        try {
            switch (action) {
                case "register":
                    return register();
                case "login":
                    return login();
                case "getAllAds":
                    return getAllAds();
                case "getAllPurchaseLogs":
                    return getAllPurchaseLogs();
                case "getAllSellLogs":
                    return getAllSellLogs();
                case "logout":
                    return logout();
                case "addAd":
                    return addAd();
                case "getTemporaryCart":
                    return getTemporaryCart();
                case "setTemporaryCart":
                    return setTemporaryCart();
                case "getAllSales":
                    return getAllSales();
                case "getAllCustomers":
                    return getAllCustomers();
                case "getAllSellers":
                    return getAllSellers();
                case "getAllAdministrators":
                    return getAllAdministrators();
                case "getAllAssistants":
                    return getAllAssistants();
                case "getAllCategories":
                    return getAllCategories();
                case "getAddProductBySellerRequests":
                    return getAddProductBySellerRequests();
                case "getAddSaleBySellerRequests":
                    return getAddSaleBySellerRequests();
                case "getEditProductBySellerRequests":
                    return getEditProductBySellerRequests();
                case "getEditSaleBySellerRequests":
                    return getEditSaleBySellerRequests();
                case "getSellerRegistrationRequests":
                    return getSellerRegistrationRequests();
                case "getAdRequests":
                    return getAdRequests();
                case "addRequest":
                    return addRequest();
                case "getAllCoupons":
                    return getAllCoupons();
                case "getAuctions":
                    return getAuctions();
                case "getAllProducts":
                    return getAllProducts();
                case "removeProduct":
                    return removeProduct();
                case "addLog":
                    return addLog();
                case "addSale":
                    return addSale();
                case "addProduct":
                    return addProduct();
                case "addCoupon":
                    return addCoupon();
                case "addCategory":
                    return addCategory();
                case "removeCoupon":
                    return removeCoupon();
                case "removeRequest":
                    return removeRequest();
                case "removeSale":
                    return removeSale();
                case "removeAccount":
                    return removeAccount();
                case "removeCategory":
                    return removeCategory();
                case "syncProducts":
                    return syncProducts();
                case "syncAuctions":
                    return syncAuctions();
                case "syncAssistantMessages":
                    return syncAssistantMessages();
                case "syncCustomers":
                    return syncCustomers();
                case "syncSellers":
                    return syncSellers();
                case "syncAdministrators":
                    return syncAdministrators();
                case "syncAssistants":
                    return syncAssistants();
                case "syncCategories":
                    return syncCategories();
                case "syncSales":
                    return syncSales();
                case "syncCoupons":
                    return syncCoupons();
                case "syncCartForUser":
                    return syncCartForUser();
                case "syncPurchaseLogs":
                    return syncPurchaseLogs();
                case "syncSellLogs":
                    return syncSellLogs();
                case "syncTemporaryCart":
                    return syncTemporaryCart();
                case "getAdminBankAccountNumber":
                    return getAdminBankAccountNumber();
                case "getOnlineUsernames":
                    return getOnlineUsernames();
                case "getOnlineAssistants":
                    return getOnlineAssistants();
                case "getMessagesForAuction":
                    return getMessagesForAuction();
                case "getAssistantMessages":
                    return getAssistantMessages();
                case "getMinimumCredit":
                    return getMinimumCredit();
                case "setMinimumCredit":
                    return setMinimumCredit();
                case "getKarmozd":
                    return getKarmozd();
                case "setKarmozd":
                    return setKarmozd();
                case "setImageForProduct":
                    return setImageForProduct();
                case "payByBank":
                    return payByBank();
                case "addCreditTapped":
                    return addCreditTapped();
                case "removeCreditTapped":
                    return removeCreditTapped();
                case "chargeBankAccount":
                    return chargeBankAccount();
                default:
                    return new StringRepresentation("wrong-action");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new StringRepresentation("wrong-action");
        }
    }

    private String getIP() {
        org.restlet.Request restletRequest = getRequest();
        ClientInfo c = restletRequest.getClientInfo();
        return c.getAddress();
//        org.restlet.Request restletRequest = getRequest();
//        HttpServletRequest r = restletRequest.getHtt

//        Map<String, String> map = getRequestHeadersInMap(ServletUtils.getRequest(restletRequest))
//        List<String> ipsList = org.restlet.Request.getCurrent().getClientInfo().getForwardedAddresses();
////        return ipsList.get(ipsList.size() - 1);
//        return getRequest().get
    }

    private Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }

        return result;
    }

    private Representation syncProducts() {
        String productsJSON = getQuery().getValues("products");
        Product[] products = new Gson().fromJson(productsJSON, Product[].class);
        DataManager.shared().setAllProducts(new ArrayList<>(Arrays.asList(products)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    // TODO: Auctions not tested

    private Representation syncAuctions() {
        String auctionsJSON = getQuery().getValues("auctions");
        Auction[] auctions = new Gson().fromJson(auctionsJSON, Auction[].class);
        DataManager.shared().setAuctions(new ArrayList<>(Arrays.asList(auctions)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncAssistantMessages() {
        String messagesJSON = getQuery().getValues("messages");
        AssistantMessage[] messages = new Gson().fromJson(messagesJSON, AssistantMessage[].class);
        DataManager.shared().setAllAssistantMessages(new ArrayList<>(Arrays.asList(messages)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncCustomers() {
        String json = getQuery().getValues("customers");
        Customer[] customers = new Gson().fromJson(json, Customer[].class);
        DataManager.shared().setAllCustomers(new ArrayList<>(Arrays.asList(customers)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncSellers() {
        String json = getQuery().getValues("sellers");
        Seller[] sellers = new Gson().fromJson(json, Seller[].class);
        DataManager.shared().setAllSellers(new ArrayList<>(Arrays.asList(sellers)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncAdministrators() {
        String json = getQuery().getValues("administrators");
        Administrator[] administrators = new Gson().fromJson(json, Administrator[].class);
        DataManager.shared().setAllAdministrators(new ArrayList<>(Arrays.asList(administrators)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncAssistants() {
        String json = getQuery().getValues("assistants");
        System.out.println("syncAssistants: " + json);
        Assistant[] assistants = new Gson().fromJson(json, Assistant[].class);
        DataManager.shared().setAllAssistants(new ArrayList<>(Arrays.asList(assistants)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncCategories() {
        String json = getQuery().getValues("categories");
        Category[] categories = new Gson().fromJson(json, Category[].class);
        DataManager.shared().setAllCategories(new ArrayList<>(Arrays.asList(categories)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncSales() {
        String json = getQuery().getValues("sales");
        Sale[] sales = new Gson().fromJson(json, Sale[].class);
        DataManager.shared().setAllSales(new ArrayList<>(Arrays.asList(sales)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncCoupons() {
        String json = getQuery().getValues("coupons");
        Coupon[] coupons = new Gson().fromJson(json, Coupon[].class);
        DataManager.shared().setAllCoupons(new ArrayList<>(Arrays.asList(coupons)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncCartForUser() {
        String json = getQuery().getValues("cart");
        String username = getQuery().getValues("username");
        String token = getQuery().getValues("token");
        Cart cart = new Gson().fromJson(json, Cart.class);
        if (token == null || token.length() != 32) return new StringRepresentation("wrong-token");
        Account account = DataManager.shared().getAccountWithGivenUsername(username);
        if (account == null) return new StringRepresentation("wrong-token");
        if (!(account instanceof Customer)) return new StringRepresentation("not-customer");
        Customer customer = (Customer) account;
        customer.setCart(cart);
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncPurchaseLogs() {
        String json = getQuery().getValues("logs");
        PurchaseLog[] purchaseLogs = new Gson().fromJson(json, PurchaseLog[].class);
        DataManager.shared().setPurchaseLogs(new ArrayList<>(Arrays.asList(purchaseLogs)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncSellLogs() {
        String json = getQuery().getValues("logs");
        SellLog[] sellLogs = new Gson().fromJson(json, SellLog[].class);
        DataManager.shared().setSellLogs(new ArrayList<>(Arrays.asList(sellLogs)));
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation syncTemporaryCart() {
        String json = getQuery().getValues("cart");
        Cart cart = new Gson().fromJson(json, Cart.class);
        DataManager.shared().setTemporaryCart(cart);
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    private Representation getAddProductBySellerRequests() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAddProductBySellerRequests()));
    }

    private Representation getAddSaleBySellerRequests() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAddSaleBySellerRequests()));
    }

    private Representation getEditProductBySellerRequests() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getEditProductBySellerRequests()));
    }

    private Representation getEditSaleBySellerRequests() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getEditSaleBySellerRequests()));
    }

    private Representation getSellerRegistrationRequests() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getSellerRegistrationRequests()));
    }

    private Representation getAdRequests() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAdRequests()));
    }

    // TODO: Brute-Force is not tested...

    private Representation login() {
        IPRecord ipRecord = new IPRecord(getIP(), LocalDateTime.now());
        if (DataManager.shared().hasMoreThanTenUnsuccessfulLoginIPRecordsIn100Seconds(ipRecord))
            return new StringRepresentation("too-many-times");
        String username = getQuery().getValues("username");
        String password = getQuery().getValues("password");
        String resultStr = "wrong-details";
        if (username == null || password == null || username.equals("") || password.equals("") ||
                username.length() > 100 || password.length() > 100) {
            DataManager.shared().addUnsuccessfulLoginIPRecord(ipRecord);
            return new StringRepresentation(resultStr);
        }

        if (DataManager.shared().doesUserWithGivenUsernameExist(username)
                && DataManager.shared().givenUsernameHasGivenPassword(username, password)) {
            resultStr = "{\"token\": \"";
            resultStr += DataManager.shared().login(username, password);
            resultStr += "\", \"account\": ";
            resultStr += new Gson().toJson(DataManager.shared().getAccountWithGivenUsername(username));
            resultStr += "}";
        }
        DataManager.saveData();
        return new StringRepresentation(resultStr);
    }

    public Representation register() {
        String username = getQuery().getValues("username");
        String password = getQuery().getValues("password");
        String email = getQuery().getValues("email");
        String phoneNumber = getQuery().getValues("phoneNumber");
        String firstName = getQuery().getValues("firstName");
        String lastName = getQuery().getValues("lastName");
        String type = getQuery().getValues("type");
        String token = getQuery().getValues("token");
        String companyDetails = getQuery().getValues("companyDetails");

        String resultStr = "wrong-details";

        if (username == null || password == null || email == null || phoneNumber == null
                || firstName == null || lastName == null || type == null
                || username.equals("") || password.equals("") || email.equals("")
                || phoneNumber.equals("") || firstName.equals("") || lastName.equals("") || type.equals("")
                || (type.equals("seller") && (companyDetails == null || companyDetails.equals(""))) ||
                username.length() > 100 || password.length() > 100 || email.length() > 100 ||
                phoneNumber.length() > 50 || firstName.length() > 150 || lastName.length() > 150 || type.length() > 25 ||
                !Validator.shared().emailIsValid(email) || !Validator.shared().phoneNumberIsValid(phoneNumber))
            return new StringRepresentation(resultStr);
        if (!isPasswordStrong(password)) return new StringRepresentation("weak-password");
        if (!DataManager.shared().doesUserWithGivenUsernameExist(username)) {
            // TODO: Profile Pic!!
            Account account = null;
            switch (type) {
                case "customer":
                    account = new Customer(username, password, email, phoneNumber, firstName, lastName, "");
                    break;
                case "seller":
                    account = new Seller(username, password, email, phoneNumber, firstName, lastName, companyDetails, "");
                    break;
                case "assistant":
                    account = new Assistant(username, password, email, phoneNumber, firstName, lastName, "");
                    break;
                case "administrator":
                    if (DataManager.shared().hasAnyAdminRegistered()) {
                        if (token != null) {
                            account = new Administrator(username, password, email, phoneNumber, firstName, lastName, "");
                            break;
                        }
                        return new StringRepresentation("admin-registration-not-allowed");
                    }
                    account = new Administrator(username, password, email, phoneNumber, firstName, lastName, "");
                    break;
            }
            if (account != null) account.setCredit(DataManager.shared().getMimimumCredit());
            DataManager.shared().registerAccount(account);
            if (!type.equals("administrator")) {
                Account finalAccount = account;
                BankAPI.tellBankAndReceiveResponse("create_account " + firstName + " " +
                        lastName + " " + username + " " + password + " " + password, response -> {
                    if (finalAccount != null) {
                        finalAccount.setBankAccountNumber(response);
                        DataManager.saveData();
                    }
                });
            }
            resultStr = "success";
        }
        DataManager.saveData();
        return new StringRepresentation(resultStr);
    }

    public Representation getAllAds() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllAds()));
    }

    public Representation getAllPurchaseLogs() {
        ArrayList<PurchaseLog> logs = DataManager.shared().getAllLogs().stream().filter(log -> log instanceof PurchaseLog)
                .map(log -> (PurchaseLog) log).collect(Collectors.toCollection(ArrayList::new));
        return new StringRepresentation(new Gson().toJson(logs));
    }

    public Representation getAllSellLogs() {
        ArrayList<SellLog> logs = DataManager.shared().getAllLogs().stream().filter(log -> log instanceof SellLog)
                .map(log -> (SellLog) log).collect(Collectors.toCollection(ArrayList::new));
        return new StringRepresentation(new Gson().toJson(logs));
    }

    public Representation logout() {
        String token = getQuery().getValues("token");
        if (token == null || token.length() != 32) return new StringRepresentation("wrong-action");
        DataManager.shared().logout(token);
        return new StringRepresentation("success");
    }

    public Representation addAd() {
        String id = getQuery().getValues("id");
        String content = getQuery().getValues("content");
        if (id == null || content == null || id.equals("") || content.equals("")
                || id.length() > 32 || content.length() > 500)
            return new StringRepresentation("wrong-action");
        DataManager.shared().addAd(new Ad(id, content));
        return new StringRepresentation("success");
    }

    public Representation getTemporaryCart() {
        String s = new Gson().toJson(DataManager.shared().getTemporaryCart());
        return new StringRepresentation(s);
    }

    public Representation setTemporaryCart() {
        String cartJSON = getQuery().getValues("cart");
        Cart cart = new Gson().fromJson(cartJSON, Cart.class);
        DataManager.shared().setTemporaryCart(cart);
        DataManager.saveData();
        return new StringRepresentation("success");
    }

    public Representation getAllSales() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllSales()));
    }

    public Representation getAllCustomers() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllCustomers()));
    }

    public Representation getAllSellers() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllSellers()));
    }

    public Representation getAllAdministrators() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllAdministrators()));
    }

    public Representation getAllAssistants() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllAssistants()));
    }

    public Representation getAllCategories() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllCategories()));
    }

    public Representation addRequest() {
        String requestJSON = getQuery().getValues("request");
        String requestType = getQuery().getValues("requestType");
        Request request;
        switch (requestType) {
            case "AddProductBySellerRequest":
                request = new Gson().fromJson(requestJSON, AddProductBySellerRequest.class);
                break;
            case "AddSaleBySellerRequest":
                request = new Gson().fromJson(requestJSON, AddSaleBySellerRequest.class);
                break;
            case "EditProductBySellerRequest":
                request = new Gson().fromJson(requestJSON, EditProductBySellerRequest.class);
                break;
            case "EditSaleBySellerRequest":
                request = new Gson().fromJson(requestJSON, EditSaleBySellerRequest.class);
                break;
            case "SellerRegistrationRequest":
                request = new Gson().fromJson(requestJSON, SellerRegistrationRequest.class);
                break;
            case "AddAdBySellerRequest":
                request = new Gson().fromJson(requestJSON, AddAdBySellerRequest.class);
                break;
            default:
                return new StringRepresentation("wrong-action");
        }
        DataManager.shared().addRequest(request);
        return new StringRepresentation("success");
    }

    public Representation getAllCoupons() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllCoupons()));
    }

    public Representation getAuctions() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAuctions()));
    }

    public Representation getAllProducts() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllProducts()));
    }

    public Representation removeProduct() {
        String productID = getQuery().getValues("id");
        if (productID == null || productID.equals("") || productID.length() > 32)
            return new StringRepresentation("wrong-action");
        DataManager.shared().removeProduct(productID);
        return new StringRepresentation("success");
    }

    public Representation addLog() {
        String logJSON = getQuery().getValues("log");
        String logType = getQuery().getValues("logType");
        Log log;
        switch (logType) {
            case "SellLog":
                log = new Gson().fromJson(logJSON, SellLog.class);
                break;
            case "PurchaseLog":
                log = new Gson().fromJson(logJSON, PurchaseLog.class);
                break;
            default:
                return new StringRepresentation("wrong-action");
        }
        DataManager.shared().addLog(log);
        return new StringRepresentation("success");
    }

    public Representation addSale() {
        String saleJSON = getQuery().getValues("sale");
        Sale sale = new Gson().fromJson(saleJSON, Sale.class);
        DataManager.shared().addSale(sale);
        return new StringRepresentation("success");
    }

    public Representation addProduct() {
        String productJSON = getQuery().getValues("product");
        Product product = new Gson().fromJson(productJSON, Product.class);
        DataManager.shared().addProduct(product);
        return new StringRepresentation("success");
    }

    public Representation addCoupon() {
        String couponJSON = getQuery().getValues("coupon");
        Coupon coupon = new Gson().fromJson(couponJSON, Coupon.class);
        DataManager.shared().addCoupon(coupon);
        return new StringRepresentation("success");
    }

    public Representation addCategory() {
        String categoryJSON = getQuery().getValues("category");
        Category category = new Gson().fromJson(categoryJSON, Category.class);
        DataManager.shared().addCategory(category);
        return new StringRepresentation("success");
    }

    public Representation removeCoupon() {
        String couponID = getQuery().getValues("id");
        if (couponID == null || couponID.equals("") || couponID.length() > 32)
            return new StringRepresentation("wrong-action");
        DataManager.shared().removeCoupon(couponID);
        return new StringRepresentation("success");
    }

    public Representation removeRequest() {
        String requestID = getQuery().getValues("id");
        if (requestID == null || requestID.equals("") || requestID.length() > 32)
            return new StringRepresentation("wrong-action");
        DataManager.shared().removeRequest(requestID);
        return new StringRepresentation("success");
    }

    public Representation removeSale() {
        String saleID = getQuery().getValues("id");
        if (saleID == null || saleID.equals("") || saleID.length() > 32)
            return new StringRepresentation("wrong-action");
        DataManager.shared().removeSale(saleID);
        return new StringRepresentation("success");
    }

    public Representation removeAccount() {
        String username = getQuery().getValues("username");
        if (username == null || username.equals("") || username.length() > 100)
            return new StringRepresentation("wrong-action");
        DataManager.shared().removeAccount(username);
        return new StringRepresentation("success");
    }

    public Representation removeCategory() {
        String categoryID = getQuery().getValues("id");
        if (categoryID == null || categoryID.equals("") || categoryID.length() > 32)
            return new StringRepresentation("wrong-action");
        DataManager.shared().removeCategory(categoryID);
        return new StringRepresentation("success");
    }

    private Representation getAdminBankAccountNumber() {
        return new StringRepresentation(DataManager.shared().getAdminBankAccountNumber());
    }

    private Representation getOnlineUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        Iterator it = DataManager.shared().getLoggedInAccountsAndTokens().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String username = (String) pair.getValue();
            if (!usernames.contains(username)) usernames.add(username);
            it.remove();
        }
        return new StringRepresentation(new Gson().toJson(usernames));
    }

    private Representation getOnlineAssistants() {
        ArrayList<String> assistants = new ArrayList<>();
        Iterator it = DataManager.shared().getLoggedInAccountsAndTokens().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String username = (String) pair.getValue();
            Account account = DataManager.shared().getAccountWithGivenUsername(username);
            if (account instanceof Assistant && !assistants.contains(username))
                assistants.add(username);
            it.remove();
        }
        return new StringRepresentation(new Gson().toJson(assistants));
    }

    private Representation getMessagesForAuction() {
        String auctionID = getQuery().getValues("auctionID");
        if (auctionID == null || auctionID.length() > 32) return new StringRepresentation("wrong-action");
        Auction auction = DataManager.shared().getAuctionWithId(auctionID);
        if (auction == null) return new StringRepresentation("wrong-action");
        return new StringRepresentation(new Gson().toJson(auction.getMessages()));
    }

    private Representation getAssistantMessages() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllAssistantMessages()));
    }

    private Representation getMinimumCredit() {
        return new StringRepresentation(String.valueOf(DataManager.shared().getMimimumCredit()));
    }

    private Representation setMinimumCredit() {
        String creditStr = getQuery().getValues("credit");
        if (creditStr == null || creditStr.length() > 100) return new StringRepresentation("wrong-action");
        try {
            int credit = Integer.parseInt(creditStr);
            if (credit < 0) return new StringRepresentation("wrong-action");
            DataManager.shared().setMimimumCredit(Integer.parseInt(creditStr));
            return new StringRepresentation("success");
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format for " + creditStr);
            return new StringRepresentation("wrong-action");
        }
    }

    // TODO: Karmozd is not tested anywhere!

    private Representation getKarmozd() {
        return new StringRepresentation(String.valueOf(DataManager.shared().getKarmozd()));
    }

    private Representation setKarmozd() {
        String karmozdStr = getQuery().getValues("karmozd");
        if (karmozdStr == null || karmozdStr.length() > 100) return new StringRepresentation("wrong-action");
        try {
            int karmozd = Integer.parseInt(karmozdStr);
            if (karmozd < 0 || karmozd >= 100) return new StringRepresentation("wrong-action");
            DataManager.shared().setKarmozd(Integer.parseInt(karmozdStr));
            return new StringRepresentation("success");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new StringRepresentation("wrong-action");
        }
    }

    private Representation setImageForProduct() {
        String productID = getQuery().getValues("productID");
        String base64 = getQuery().getValues("base64");
        if (productID == null || base64 == null || productID.length() > 50)
            return new StringRepresentation("wrong-action");
        try (PrintStream out = new PrintStream(new FileOutputStream(productID + ".txt"))) {
            out.print(base64);
            return new StringRepresentation("success");
        } catch (IOException e) {
            System.out.println("Unexpected exception happened in saving data: " + e.getLocalizedMessage());
            return new StringRepresentation("wrong-action");
        }
    }

    private Representation payByBank() {
        String username = getQuery().getValues("username");
        String password = getQuery().getValues("password");
        String customerBankAccountNumber = getQuery().getValues("customerBankAccountNumber");
        String adminBankAccountNumber = getQuery().getValues("adminBankAccountNumber");
        String priceAfterDiscount = getQuery().getValues("priceAfterDiscount");

        BankAPI.tellBankAndReceiveResponse("get_token " + username + " " + password, token ->
                BankAPI.tellBankAndReceiveResponse("create_receipt " + token + " " +
                        "move" + " " + (int) Double.parseDouble(priceAfterDiscount) + " " + customerBankAccountNumber +
                        " " + adminBankAccountNumber + " pBBT", receiptID ->
                        BankAPI.tellBankAndReceiveResponse("pay " + receiptID, response -> {

                        })));
        return new StringRepresentation("success");
    }

    private Representation addCreditTapped() {
        String username = getQuery().getValues("username");
        String password = getQuery().getValues("password");
        String credit = getQuery().getValues("credit");
        String accountBankAccountNumber = getQuery().getValues("accountBankAccountNumber");
        String adminBankAccountNumber = getQuery().getValues("adminBankAccountNumber");
        Account account = DataManager.shared().getAccountWithGivenUsername(username);
        account.setCredit(account.getCredit() + Integer.parseInt(credit));
        BankAPI.tellBankAndReceiveResponse("get_token " + username + " " + password, token -> {
            BankAPI.tellBankAndReceiveResponse("create_receipt " + token + " " +
                    "move" + " " + (int) Double.parseDouble(credit) + " " +
                    adminBankAccountNumber + " " + accountBankAccountNumber + " move", receiptID ->
                    BankAPI.tellBankAndReceiveResponse("pay " + receiptID, response -> {

                            }
                    ));
        });
        return new StringRepresentation("success");
    }

    private Representation removeCreditTapped() {
        String username = getQuery().getValues("username");
        String password = getQuery().getValues("password");
        String credit = getQuery().getValues("credit");
        String accountBankAccountNumber = getQuery().getValues("accountBankAccountNumber");
        Account account = DataManager.shared().getAccountWithGivenUsername(username);
        account.setCredit(account.getCredit() - Integer.parseInt(credit));
        BankAPI.tellBankAndReceiveResponse("get_token " + username + " " + password, token -> {
            BankAPI.tellBankAndReceiveResponse("create_receipt " + token + " " +
                    "deposit" + " " + (int) Double.parseDouble(credit) + " " +
                    "-1" + " " + accountBankAccountNumber + " deposit", receiptID ->
                    BankAPI.tellBankAndReceiveResponse("pay " + receiptID, response ->
                    {
                    }));
        });
        return new StringRepresentation("success");
    }

    private Representation chargeBankAccount() {
        String username = getQuery().getValues("username");
        String password = getQuery().getValues("password");
        String credit = getQuery().getValues("credit");
        String accountBankAccountNumber = getQuery().getValues("accountBankAccountNumber");

        BankAPI.tellBankAndReceiveResponse("get_token " + username + " " + password, token -> {
            BankAPI.tellBankAndReceiveResponse("create_receipt " + token + " " +
                    "deposit" + " " + (int) Double.parseDouble(credit) + " " +
                    "-1" + " " + accountBankAccountNumber + " deposit", receiptID ->
                    BankAPI.tellBankAndReceiveResponse("pay " + receiptID, response ->
                    {
                    }));
        });
        return new StringRepresentation("success");
    }

    // TODO: Better password strength alg... both in server and client!

//    private boolean isPasswordStrong(String password) {
//        return true;
//    }

    private boolean isPasswordStrong(String password) {
        if (DataManager.commonPasswords.contains(password)) return false;
        if (password.length() < 8) return false;
        int charCount = 0, numCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (isNumeric(ch)) numCount++;
            else if (isLetter(ch)) charCount++;
            else return false;
        }
        return (charCount >= 2 && numCount >= 2);
    }

    private boolean isLetter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }


    private boolean isNumeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }
}
