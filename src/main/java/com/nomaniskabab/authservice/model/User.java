package com.nomaniskabab.authservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name; // 👈 This must exist!

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}
