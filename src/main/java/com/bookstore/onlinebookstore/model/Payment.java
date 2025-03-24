package com.bookstore.onlinebookstore.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String transactionReference;

    private LocalDateTime paymentDate;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

}
