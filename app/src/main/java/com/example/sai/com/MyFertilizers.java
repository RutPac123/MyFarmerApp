package com.example.sai.com;

public class MyFertilizers {

    private String Name, Description, Price;

    public MyFertilizers() {

    }

    public MyFertilizers(String name, String description, String price) {
        Name = name;
        Description = description;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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


