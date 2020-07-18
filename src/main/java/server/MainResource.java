package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

// TODO: IMPORTANT: KARMOZD!!

public class MainResource extends ServerResource {
    @Get
    public Representation getAction() {
        DataManager.loadData();
        if (!DataManager.shared().isMadeAdminBankAccount()) {
            BankAPI.tellBankAndReceiveResponse("create_account Admin Admin admin admin admin", response -> {
                DataManager.shared().setAdminBankAccountNumber(response);
                DataManager.saveData();
            });
        }
        String action = getQuery().getValues("action");
        if (action == null) return new StringRepresentation("wrong-action");
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
            case "syncCustomers":
                return syncCustomers();
            case "syncSellers":
                return syncSellers();
            case "syncAdministrators":
                return syncAdministrators();
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
            default:
                return new StringRepresentation("wrong-action");
        }
    }

    private Representation syncProducts() {
        String productsJSON = getQuery().getValues("products");
        Product[] products = new Gson().fromJson(productsJSON, Product[].class);
        DataManager.shared().setAllProducts(new ArrayList<>(Arrays.asList(products)));
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
        String token = getQuery().getValues("token");
        Cart cart = new Gson().fromJson(json, Cart.class);
        Account account = DataManager.shared().getAccountWithToken(token);
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

    private Representation login() {
        String username = getQuery().getValues("username");
        String password = getQuery().getValues("password");
        String resultStr = "wrong-details";

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
        String companyDetails = getQuery().getValues("companyDetails");

        String resultStr = "wrong-details";

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
                case "administrator":
                    if (DataManager.shared().hasAnyAdminRegistered())
                        return new StringRepresentation("admin-registration-not-allowed");
                    account = new Administrator(username, password, email, phoneNumber, firstName, lastName, "");
                    break;
            }
            DataManager.shared().registerAccount(account);
            if (!type.equals("administrator")) {
                Account finalAccount = account;
                BankAPI.tellBankAndReceiveResponse("create_account " + firstName + " " + lastName + " " + username + " " + password + " " + password, response -> {
                    if (finalAccount != null) finalAccount.setBankAccountNumber(response);
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
        DataManager.shared().logout(getQuery().getValues("token"));
        return new StringRepresentation("success");
    }

    public Representation addAd() {
        Ad ad = new Ad(getQuery().getValues("id"), getQuery().getValues("content"));
        DataManager.shared().addAd(ad);
        return new StringRepresentation("success");
    }

    public Representation getTemporaryCart() {
        String s = new Gson().toJson(DataManager.shared().getTemporaryCart());
        System.out.println("SSS= " + s);
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

    public Representation getAllCategories() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllCategories()));
    }

    public Representation addRequest() {
        String requestJSON = getQuery().getValues("request");
        String requestType = getQuery().getValues("requestType");
        Request request = null;
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
        }
        DataManager.shared().addRequest(request);
        return new StringRepresentation("success");
    }

    public Representation getAllCoupons() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllCoupons()));
    }

    public Representation getAllProducts() {
        return new StringRepresentation(new Gson().toJson(DataManager.shared().getAllProducts()));
    }

    public Representation removeProduct() {
        String productID = getQuery().getValues("id");
        DataManager.shared().removeProduct(productID);
        return new StringRepresentation("success");
    }

    public Representation addLog() {
        String logJSON = getQuery().getValues("log");
        String logType = getQuery().getValues("logType");
        Log log = null;
        switch (logType) {
            case "SellLog":
                log = new Gson().fromJson(logJSON, SellLog.class);
                break;
            case "PurchaseLog":
                log = new Gson().fromJson(logJSON, PurchaseLog.class);
                break;
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
        DataManager.shared().removeCoupon(couponID);
        return new StringRepresentation("success");
    }

    public Representation removeRequest() {
        String requestID = getQuery().getValues("id");
        DataManager.shared().removeRequest(requestID);
        return new StringRepresentation("success");
    }

    public Representation removeSale() {
        String saleID = getQuery().getValues("id");
        DataManager.shared().removeSale(saleID);
        return new StringRepresentation("success");
    }

    public Representation removeAccount() {
        String username = getQuery().getValues("username");
        DataManager.shared().removeAccount(username);
        return new StringRepresentation("success");
    }

    public Representation removeCategory() {
        String categoryID = getQuery().getValues("id");
        DataManager.shared().removeCategory(categoryID);
        return new StringRepresentation("success");
    }

    private Representation getAdminBankAccountNumber() {
        return new StringRepresentation(DataManager.shared().getAdminBankAccountNumber());
    }
}