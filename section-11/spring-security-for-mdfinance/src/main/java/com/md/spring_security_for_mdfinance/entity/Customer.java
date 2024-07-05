package com.md.spring_security_for_mdfinance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author Maulik Davra
 * @Created on July 5th 2024
 * <br/>
 * <br/>Customer entity class is used to map the customer details to the database.
 * <br/>It is used to map the customer name, email, mobile number and password to the database.
 * <br/> After the customer details are fetched via restTemplate, they are mapped to the Customer entity class and saved to the database.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "customer_name")
    private String name;
    @Column(name = "customer_email")
    private String email;
    @Column(name = "customer_mobile_number")
    private String mobileNumber;
    @Column(name = "customer_password")
    private String password;
}
