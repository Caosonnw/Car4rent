package com.example.car4rent;

public class Cars {
    String name,price,description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cars(String name, String price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
