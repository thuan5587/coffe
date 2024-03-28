package com.thanhthuan.coffe;

public class categories {

    private String product_id;
    private String name;
    private String type;
    private Double price;
    private String status;

    // Corrected constructor definition and placement of curly braces
    public categories(String productID, String name, String type, Double price, String status) {
        this.product_id = productID;
        this.name = name;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public String getProduct_id() {
        return product_id;
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
