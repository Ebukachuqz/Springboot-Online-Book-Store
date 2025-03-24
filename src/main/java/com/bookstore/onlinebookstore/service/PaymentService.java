package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.model.Payment;
import com.bookstore.onlinebookstore.model.PaymentMethod;
import com.bookstore.onlinebookstore.model.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    public Payment processPayment(double amount, PaymentMethod paymentMethod) {

        // Create new payment record
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionReference(generateTransactionReference());

        // Simulate payment processing based on method
        return switch (paymentMethod) {
            case WEB -> simulateWebPayment(payment);
            case USSD -> simulateUssdPayment(payment);
            case TRANSFER -> simulateTransferPayment(payment);
            default -> {
                payment.setStatus(PaymentStatus.FAILED);
                yield payment;
            }
        };
    }

    private Payment simulateWebPayment(Payment payment) {
        simulateDelay(3000);

        // Assume 95% success rate for simulation
        if (Math.random() < 0.95) {
            payment.setStatus(PaymentStatus.COMPLETED);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }
        return payment;
    }

    private Payment simulateUssdPayment(Payment payment) {
        simulateDelay(4000);

        // Assume 90% success rate for simulation
        if (Math.random() < 0.90) {
            payment.setStatus(PaymentStatus.COMPLETED);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }
        return payment;
    }

    private Payment simulateTransferPayment(Payment payment) {
        simulateDelay(5500);

        // Assume 85% success rate for simulation
        if (Math.random() < 0.85) {
            payment.setStatus(PaymentStatus.COMPLETED);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }
        return payment;
    }

    private void simulateDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String generateTransactionReference() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
