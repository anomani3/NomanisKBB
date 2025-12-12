package com.nomaniskabab.payment_sevice.dto;
import lombok.Data;

@Data
public class PaymentRequest {
    private Long userId;
    private String paymentMethod; // CARD / UPI / CASH
}

