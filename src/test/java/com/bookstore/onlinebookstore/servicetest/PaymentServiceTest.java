package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.model.Payment;
import com.bookstore.onlinebookstore.model.PaymentMethod;
import com.bookstore.onlinebookstore.model.PaymentStatus;
import com.bookstore.onlinebookstore.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }

    @Test
    void processPayment_webPayment_successOrFail() {
        // Act
        Payment payment = paymentService.processPayment(100.0, PaymentMethod.WEB);

        // Assert
        // Payment can be COMPLETED or FAILED (random chance).
        assertNotNull(payment);
        assertEquals(100.0, payment.getAmount());
        assertNotNull(payment.getTransactionReference());
        assertNotNull(payment.getPaymentDate());
        assertTrue(payment.getStatus() == PaymentStatus.COMPLETED ||
                payment.getStatus() == PaymentStatus.FAILED);
    }

    @Test
    void processPayment_ussdPayment_successOrFail() {
        // Act
        Payment payment = paymentService.processPayment(50.0, PaymentMethod.USSD);

        // Assert
        assertNotNull(payment);
        assertEquals(50.0, payment.getAmount());
        assertTrue(payment.getStatus() == PaymentStatus.COMPLETED ||
                payment.getStatus() == PaymentStatus.FAILED);
    }

    @Test
    void processPayment_transferPayment_successOrFail() {
        // Act
        Payment payment = paymentService.processPayment(200.0, PaymentMethod.TRANSFER);

        // Assert
        assertEquals(200.0, payment.getAmount());
        assertTrue(payment.getStatus() == PaymentStatus.COMPLETED ||
                payment.getStatus() == PaymentStatus.FAILED);
    }

    @Test
    void processPayment_unknownMethod_returnsFailed() {
        // Act
        Payment payment = paymentService.processPayment(300.0, null);

        // Assert
        assertEquals(PaymentStatus.FAILED, payment.getStatus());
    }
}
