package com.nomaniskabab.payment_sevice.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double totalAmount;

    private String paymentStatus; // SUCCESS / FAILED

    private String paymentMethod; // CARD, UPI, CASH

    private String transactionId;
}

