package com.thanhthuan.coffe;

public class product {
    private String productId;
    private String productName;
    private String type;
    private double price;
    private int quantity;
    private int id;

    // Constructor
    public product(int id, String productId, String productName, String type, double price, int quantity) {
        this.id=id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }
    public Integer getId(){
        return id;
    }
    public String getProductId(){
        return productId;
    }
    public String getProductName(){
        return productName;
    }
    public String getType(){
        return type;
    }
    public Double getPrice(){
        return price;
    }
    public Integer getQuantity(){
        return quantity;
    }

}
