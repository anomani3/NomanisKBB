package com.nomaniskabab.cartservice.dto;

import lombok.Data;

@Data
public class CartResponse {
    private Long id;
    private Long userId;
    private Long menuItemId;
    private String itemName;
    private double price;
    private int quantity;
    private String imageUrl;

    private double totalItemPrice;
}
