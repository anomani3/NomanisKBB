package com.nomaniskabab.payment_sevice.controller;
import com.nomaniskabab.payment_sevice.dto.PaymentRequest;
import com.nomaniskabab.payment_sevice.entity.Payment;
import com.nomaniskabab.payment_sevice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public Payment makePayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}

