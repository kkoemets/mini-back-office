package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Iterable<Customer> list();

    Customer save(Customer customer);

    Optional<Customer> findById(long id);

    Iterable<Customer> saveAll(List<Customer> customers);
}
