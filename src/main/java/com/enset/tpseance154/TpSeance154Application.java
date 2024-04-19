package com.enset.tpseance154;

import com.enset.tpseance154.entites.Payment;
import com.enset.tpseance154.entites.PaymentStatus;
import com.enset.tpseance154.entites.PaymentType;
import com.enset.tpseance154.entites.Student;
import com.enset.tpseance154.repository.PaymentRepository;
import com.enset.tpseance154.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class TpSeance154Application {

    public static void main(String[] args) {
        SpringApplication.run(TpSeance154Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        return args -> {

            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("Mohamed").lastName("Ali").code("g123").programId("GLSID")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("Oussama").lastName("Elhachimi").code("a123").programId("BDCC").build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("Yassine").lastName("Amrani").code("b123").programId("GLSID").build());
            PaymentType[] paymentTypes = PaymentType.values();
            Random random = new Random();

            studentRepository.findAll().forEach(student -> {
                for (int i = 0; i < 10; i++) {
                    int index = random.nextInt(paymentTypes.length);
                    Payment payment = Payment.builder().amount(1000 + (int) (Math.random() * 20000)).paymentType(paymentTypes[index]).paymentStatus(PaymentStatus.CREATED).paymentDate(LocalDate.now()).student(student).build();
                    paymentRepository.save(payment);
                }
            });

        };
    }

}
