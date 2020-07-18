package model;

public class Request {
    private String id;

    public Request(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void fulfill() {}

    @Override
    public String toString() {
        if (this instanceof AddProductBySellerRequest) {
            AddProductBySellerRequest request = (AddProductBySellerRequest) this;
            return "فروشنده " + request.getSeller().getFirstName() + " " + request.getSeller().getLastName() + " تمایل به افزودن کالای "
                    + request.getProduct().getName() + " دارد" + "\n" + "وضعیت درخواست: در انتظار تایید توسط مدیر";
        } else if (this instanceof AddSaleBySellerRequest) {
            AddSaleBySellerRequest request = (AddSaleBySellerRequest) this;
            return "فروشنده " + request.getSeller().getFirstName() + " " + request.getSeller().getLastName() + " تمایل به افزودن حراج "
                    + request.getSale().getOffId() + " دارد"  + "\n" + "وضعیت درخواست: در انتظار تایید توسط مدیر";
        } else if (this instanceof EditProductBySellerRequest) {
            EditProductBySellerRequest request = (EditProductBySellerRequest) this;
            return "فروشنده " + request.getSeller().getFirstName() + " " + request.getSeller().getLastName() + " تمایل به ویرایش کالای "
                    + request.getOldProduct().getName() + " دارد" + "\n" + "وضعیت درخواست: در انتظار تایید توسط مدیر";
        } else if (this instanceof EditSaleBySellerRequest) {
            EditSaleBySellerRequest request = (EditSaleBySellerRequest) this;
            return "فروشنده " + request.getSeller().getFirstName() + " " + request.getSeller().getLastName() + " تمایل به ویرایش حراج "
                    + request.getOldSale().getOffId() + " دارد" + "\n" + "وضعیت درخواست: در انتظار تایید توسط مدیر";
        } else if (this instanceof SellerRegistrationRequest) {
            SellerRegistrationRequest request = (SellerRegistrationRequest) this;
            return "فروشنده " + request.getSeller().getFirstName() + " " + request.getSeller().getLastName() + " از شرکت "
                    + request.getSeller().getCompanyDetails() +  " تمایل به ثبت نام دارد" + "\n" + "وضعیت درخواست: در انتظار تایید توسط مدیر";
        } else if (this instanceof AddAdBySellerRequest) {
            AddAdBySellerRequest request = (AddAdBySellerRequest) this;
            return "فروشنده " + request.getSeller().getFirstName() + " " + request.getSeller().getLastName() + " از شرکت "
                    + request.getSeller().getCompanyDetails() +  " قصد افزودن تبلیغ"   + " را دارد\n" + "وضعیت درخواست: در انتظار تایید توسط مدیر";

        } else return "ID #" + id;
    }
}
