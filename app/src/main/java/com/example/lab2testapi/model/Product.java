package com.example.lab2testapi.model;

public class Product {
    public  String name;
    public float price;
    public  String description;
    public  String CreatedAt;
    public  String UpdatedAt;

    public Product(String name, float price, String description, String createdAt, String updatedAt) {
        this.name = name;
        this.price = price;
        this.description = description;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }
}
