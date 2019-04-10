package com.example.sai.com.Seeds;

public class MySeeds {
    private String Name,Image,Description,Price,MenuId,Discount;

    public MySeeds(){

    }

    public MySeeds(String name, String image, String description, String price, String menuId,String discount) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        MenuId = menuId;
        Discount = discount;

    }


    public String getDiscount() {
        return Discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

}
