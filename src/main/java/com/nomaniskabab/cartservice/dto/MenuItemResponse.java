package com.nomaniskabab.cartservice.dto;

import lombok.Data;

@Data
public class MenuItemResponse {
    private Long id;
    private String name;
    private String category;
    private double price;
    private String description;
    private String imageUrl;
}
