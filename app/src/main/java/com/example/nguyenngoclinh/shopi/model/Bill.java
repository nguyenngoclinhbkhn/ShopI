package com.example.nguyenngoclinh.shopi.model;

public class Bill {
    private String idBill;
    private String nameProductBill;
    private String sumPriceBill;
    private String quantityProductBill;
    private int resourceImageBill;
    private String statusBill;
    private String idProduct;
    private String addressUser;
    private String imageProduct;

    public Bill(){}

    public Bill(String idBill,
                String sumPriceBill,
                int resourceImageBill,
                String statusBill,
                String quantityProductBill,
                String nameProductBill,
                String idProduct,
                String addressUser,
                String imageProduct) {
        this.idBill = idBill;
        this.quantityProductBill = quantityProductBill;
        this.nameProductBill = nameProductBill;
        this.sumPriceBill = sumPriceBill;
        this.resourceImageBill = resourceImageBill;
        this.statusBill = statusBill;
        this.idProduct = idProduct;
        this.addressUser = addressUser;
        this.imageProduct = imageProduct;
    }
    public void setImageProduct(String imageProduct){
        this.imageProduct = imageProduct;
    }
    public String getImageProduct(){
        return imageProduct;
    }
    public void setAddressUser(String addressUser){
        this.addressUser = addressUser;
    }
    public String getAddressUser(){
        return addressUser;
    }
    public void setIdProduct(String idProduct){
        this.idProduct = idProduct;
    }
    public String getIdProduct(){
        return idProduct;
    }
    public void setNameProductBill(String nameProductBill){
        this.nameProductBill = nameProductBill;
    }
    public String getNameProductBill(){
        return nameProductBill;
    }
    public void setQuantityProductBill(String quantityProductBill){
        this.quantityProductBill = quantityProductBill;
    }
    public String getQuantityProductBill(){
        return quantityProductBill;
    }

    public void setStatusBill(String statusBill){
        this.statusBill = statusBill;
    }
    public String getStatusBill(){
        return statusBill;
    }
    public int getResourceImageBill() {
        return resourceImageBill;
    }

    public void setResourceImageBill(int resourceImageBill) {
        this.resourceImageBill = resourceImageBill;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getSumPriceBill() {
        return sumPriceBill;
    }

    public void setSumPriceBill(String sumPriceBill) {
        this.sumPriceBill = sumPriceBill;
    }
}
