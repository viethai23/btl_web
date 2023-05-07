package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.Entity.Customer;
import com.btl_web.btl_web.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }
}
