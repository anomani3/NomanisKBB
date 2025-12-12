package com.nomaniskabab.payment_sevice.repository;
import com.nomaniskabab.payment_sevice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

