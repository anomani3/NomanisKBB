package com.nomaniskabab.cartservice.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long userId;
    private Long menuItemId;
    private int quantity;
}
