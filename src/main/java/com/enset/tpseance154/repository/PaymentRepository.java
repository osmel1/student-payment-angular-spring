package com.enset.tpseance154.repository;

import com.enset.tpseance154.entites.Payment;
import com.enset.tpseance154.entites.PaymentStatus;
import com.enset.tpseance154.entites.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    List<Payment> findByStudentCode(String code);
    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
    List<Payment> findByPaymentType(PaymentType paymentType);
}
