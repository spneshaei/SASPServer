package model;

public class AddSaleBySellerRequest extends Request {
    private String seller;
    private Sale sale;

    public AddSaleBySellerRequest(String id, Seller seller, Sale sale) {
        super(id);
        this.seller = seller.getUsername();
        this.sale = sale;
    }

    public Seller getSeller() {
        return (Seller)(DataManager.shared().getAccountWithGivenUsername(seller));
    }

    public Sale getSale() {
        return sale;
    }

    @Override
    public void fulfill() {
        DataManager.shared().addSale(getSale());
    }
}
