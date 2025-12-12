package com.nomaniskabab.menuservice.dto;

import lombok.Data;

@Data
public class MenuResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Double price;
}
