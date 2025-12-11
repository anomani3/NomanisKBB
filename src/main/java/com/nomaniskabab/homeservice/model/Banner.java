package com.nomaniskabab.homeservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String imageUrl;
}
