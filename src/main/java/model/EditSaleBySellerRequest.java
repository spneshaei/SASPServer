package model;

public class EditSaleBySellerRequest extends Request {
    private String seller;
    private String oldSale;
    private Sale newSale;

    public EditSaleBySellerRequest(String id, Seller seller, Sale oldSale, Sale newSale) {
        super(id);
        this.seller = seller.getUsername();
        this.oldSale = oldSale.getOffId();
        this.newSale = newSale;
    }

    public Sale getNewSale() {
        return newSale;
    }

    public Sale getOldSale() {
        return DataManager.shared().getSaleWithId(oldSale);
    }

    public Seller getSeller() {
        return (Seller) DataManager.shared().getAccountWithGivenUsername(seller);
    }

    @Override
    public void fulfill() {
        DataManager.shared().removeSale(getOldSale().getOffId());
        DataManager.shared().addSale(getNewSale());
    }
}
