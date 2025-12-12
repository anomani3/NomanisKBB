package com.nomaniskabab.menuservice.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class MenuRequest {

    @NotBlank(message = "Food name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;
}

