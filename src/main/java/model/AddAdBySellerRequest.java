package model;

public class AddAdBySellerRequest extends Request {
    private String seller;
    private String ad;

    public AddAdBySellerRequest(String id, Seller seller, Ad ad) {
        super(id);
        this.seller = seller.getUsername();
        this.ad = ad.getContent();
    }

    public Seller getSeller() {
        return (Seller)(DataManager.shared().getAccountWithGivenUsername(seller));
    }

    @Override
    public void fulfill() {
        DataManager.shared().addAd(new Ad(DataManager.getNewId(), ad));
    }
}
