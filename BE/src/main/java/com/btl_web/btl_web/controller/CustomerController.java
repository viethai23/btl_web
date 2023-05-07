package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.Entity.Customer;
import com.btl_web.btl_web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAllCustomer();
    }
}
