package model;

public class AddProductBySellerRequest extends Request {
    private String seller;
    private Product product;

    public AddProductBySellerRequest(String id, Seller seller, Product product) {
        super(id);
        this.seller = seller.getUsername();
        this.product = product;
    }

    public Seller getSeller() {
        return (Seller)(DataManager.shared().getAccountWithGivenUsername(seller));
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public void fulfill() {
        DataManager.shared().addProduct(getProduct());
    }
}
