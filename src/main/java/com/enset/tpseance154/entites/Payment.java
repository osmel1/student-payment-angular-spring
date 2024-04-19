package com.enset.tpseance154.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity @NoArgsConstructor
@AllArgsConstructor @Getter @Setter @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate paymentDate;
    private double amount;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private String file;
    @ManyToOne
    private Student student;
}
