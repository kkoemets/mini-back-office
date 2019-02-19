package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Customer;
import com.pocopay.minibackoffice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Iterable<Customer> list() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(long id) {
        Optional<Customer> result = customerRepository.findById(id);
        return result.isPresent() ? result.get() : null;

    }

    @Override
    public Iterable<Customer> saveAll(List<Customer> customers) {
        return customerRepository.saveAll(customers);
    }
}
