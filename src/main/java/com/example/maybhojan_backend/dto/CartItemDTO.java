
package com.example.maybhojan_backend.dto;

public class CartItemDTO {

    private Long cartId;
    private Long foodId;
    private String name;
    private Double price;
    private String chef;
    private int qty;

    public CartItemDTO(Long cartId, Long foodId, String name, Double price, String chef, int qty) {
        this.cartId = cartId;
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.chef = chef;
        this.qty = qty;
    }

    public Long getCartId() { return cartId; }
    public Long getFoodId() { return foodId; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public String getChef() { return chef; }
    public int getQty() { return qty; }
}