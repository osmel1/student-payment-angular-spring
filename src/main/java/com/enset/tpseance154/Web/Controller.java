package com.enset.tpseance154.Web;

import com.enset.tpseance154.entites.Payment;
import com.enset.tpseance154.entites.PaymentStatus;
import com.enset.tpseance154.entites.PaymentType;
import com.enset.tpseance154.entites.Student;
import com.enset.tpseance154.repository.PaymentRepository;
import com.enset.tpseance154.repository.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class Controller {
    PaymentRepository paymentRepository;
    StudentRepository studentRepository;

    public Controller(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }
    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }
    @GetMapping("/payment/{id}")
    public Payment getPaymentByid(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    @GetMapping("/paymentsByStatus")
    public List<Payment> getPaymentsByStatus(@RequestParam PaymentStatus status) {
        return paymentRepository.findByPaymentStatus(status);
    }
    @GetMapping("/paymentsByType")
    public List<Payment> getPaymentsByType(@RequestParam PaymentType type) {
        return paymentRepository.findByPaymentType(type);
    }
    @PutMapping("/payment/{id}")
    public Payment updatePayment(@PathVariable Long id, @RequestParam PaymentStatus status) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }

    @PostMapping(path="/payment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam  MultipartFile file, LocalDate paymentDate,double paymentAmount, PaymentType paymentType, String studentCode) throws IOException {
     // create or go the folder if exists
        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-tp-seance-15-4", "payments");
        if(!Files.exists(folderPath)){
            try {
                Files.createDirectories(folderPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // save the file
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-tp-seance-15-4", "payments", fileName+".pdf");
        Files.copy(file.getInputStream(), filePath);
        // save the payment
        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder().file(filePath.toUri().toString()).paymentDate(paymentDate).amount(paymentAmount).paymentType(paymentType).student(student).paymentStatus(PaymentStatus.CREATED).build();
        return paymentRepository.save(payment);
    }

    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }
    @GetMapping("/student/{code}")
    public Student getStudentById(@PathVariable String code){
        return studentRepository.findByCode(code);
    }
    @GetMapping("/stdents/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping("/studentsByProgrameId/{programId}")
    public List<Student> getStudentsByprogrameId(String programId){
        return studentRepository.findByProgramId(programId);
    }

}
