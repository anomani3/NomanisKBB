package com.nomaniskabab.payment_sevice.dto;
import lombok.Data;

@Data
public class CartItemResponse {
    private Long menuItemId;
    private String name;
    private double price;
    private int quantity;
    private double totalPrice;
    private String imageUrl;
}

