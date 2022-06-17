package com.example.appfood.Model;

public class Orders {
    public int id;
    public int orderCode;
    public int productCode;
    public String nameDetails;
    public String Image;
    public long totalPrice;
    public int quantity;

    public Orders(int id, int orderCode, int productCode, String nameDetails, String image, long totalPrice, int quantity) {
        this.id = id;
        this.orderCode = orderCode;
        this.productCode = productCode;
        this.nameDetails = nameDetails;
        Image = image;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getNameDetails() {
        return nameDetails;
    }

    public void setNameDetails(String nameDetails) {
        this.nameDetails = nameDetails;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
