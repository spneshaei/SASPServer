package model;

public class EditProductBySellerRequest extends Request {
    private String seller;
    private String oldProduct;
    private Product newProduct;

    public EditProductBySellerRequest(String id, Seller seller, Product oldProduct, Product newProduct) {
        super(id);
        this.seller = seller.getUsername();
        this.oldProduct = oldProduct.getProductId();
        this.newProduct = newProduct;
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }

    public Product getOldProduct() {
        return DataManager.shared().getProductWithId(oldProduct);
    }

    public Product getNewProduct() {
        return newProduct;
    }

    @Override
    public void fulfill() {
        DataManager.shared().removeProduct(oldProduct);
        DataManager.shared().addProduct(getNewProduct());
    }
}
