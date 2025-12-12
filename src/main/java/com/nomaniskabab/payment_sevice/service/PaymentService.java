package com.nomaniskabab.payment_sevice.service;
import com.nomaniskabab.payment_sevice.dto.CartItemResponse;
import com.nomaniskabab.payment_sevice.dto.NotificationRequest;
import com.nomaniskabab.payment_sevice.dto.PaymentRequest;
import com.nomaniskabab.payment_sevice.entity.Payment;
import com.nomaniskabab.payment_sevice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    private final String CART_SERVICE_URL = "http://localhost:8085/api/cart/";

    public Payment processPayment(PaymentRequest request) {

        // 1️⃣ Fetch all user's cart items
        CartItemResponse[] cartItems = restTemplate.getForObject(
                CART_SERVICE_URL + request.getUserId(),
                CartItemResponse[].class
        );

        if (cartItems == null || cartItems.length == 0) {
            throw new RuntimeException("Cart is empty!");
        }

        // 2️⃣ Calculate total amount
        double total = Arrays.stream(cartItems)
                .mapToDouble(CartItemResponse::getTotalPrice)
                .sum();

        // 3️⃣ Generate transaction ID
        String txnId = UUID.randomUUID().toString();

        // 4️⃣ Save payment
        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .paymentMethod(request.getPaymentMethod())
                .totalAmount(total)
                .paymentStatus("SUCCESS")
                .transactionId(txnId)
                .build();

        Payment saved = paymentRepository.save(payment);

        restTemplate.postForObject(
                "http://localhost:8087/api/notification/send",
                new NotificationRequest(
                        request.getUserId(),
                        "Your payment was successful. Transaction ID: " + txnId,
                        "PAYMENT"
                ),
                Void.class
        );


        // 5️⃣ Clear cart after payment
        restTemplate.delete(CART_SERVICE_URL + "clear/" + request.getUserId());

        return saved;
    }

}

