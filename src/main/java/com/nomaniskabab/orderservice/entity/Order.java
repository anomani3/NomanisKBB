package com.nomaniskabab.orderservice.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;     // who placed the order
    private String items;    // JSON list of items
    private double totalAmount;

    private String status = "PENDING";  // PENDING, CONFIRMED, CANCELLED, DELIVERED
    private LocalDateTime orderDate = LocalDateTime.now();
}
