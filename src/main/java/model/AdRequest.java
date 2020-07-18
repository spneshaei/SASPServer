package model;

public class AdRequest extends Request {
    private final static int fee = 5;
    private Ad ad;
    private Account requester;
    public AdRequest(String id, Ad ad, Account requester) {
        super(id);
        this.ad = ad;
        this.requester = requester;
        requester.decreaseCredit(fee);
    }

    @Override
    public void fulfill() {
        DataManager.shared().addAd(ad);
    }
}
