package com.nomaniskabab.homeservice.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String phone;
}

