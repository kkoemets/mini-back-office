package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Customer;

import java.util.List;

public interface CustomerService {

    Iterable<Customer> list();

    Customer save(Customer customer);

    Customer findById(long id);

    Iterable<Customer> saveAll(List<Customer> customers);
}
