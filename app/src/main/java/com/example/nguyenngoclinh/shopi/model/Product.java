package com.example.nguyenngoclinh.shopi.model;

public class Product {
    private String idProduct;
    private String nameProduct;
    private String priceProduct;
    private String kindProduct;
    private String imageProduct;
    private String commentProduct;
    private String quantity;

    public Product(){}

    public Product(String idProduct,
                   String nameProduct,
                   String priceProduct,
                   String kindProduct,
                   String imageProduct,
                   String commentProduct,
                   String quantity){
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.kindProduct = kindProduct;
        this.imageProduct = imageProduct;
        this.commentProduct = commentProduct;
        this.quantity =  quantity;
    }

    public String getQuantity(){
        return quantity;
    }

    public void setQuantity(String quantity){
        this.quantity = quantity;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getKindProduct() {
        return kindProduct;
    }

    public void setKindProduct(String kindProduct) {
        this.kindProduct = kindProduct;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public void setCommentProduct(String commentProduct){
        this.commentProduct = commentProduct;
    }

    public String getCommentProduct(){
        return commentProduct;
    }
}
