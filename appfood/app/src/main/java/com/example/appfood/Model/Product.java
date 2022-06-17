package com.example.appfood.Model;

import java.io.Serializable;

public class Product implements Serializable {
    public int Id;
    public String pName;
    public int Price;
    public String Image;
    public String Description;
    public int idCategory;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public Product(int id, String pName, int price, String image, String description, int idCategory) {
        Id = id;
        this.pName = pName;
        Price = price;
        Image = image;
        Description = description;
        this.idCategory = idCategory;
    }
}
