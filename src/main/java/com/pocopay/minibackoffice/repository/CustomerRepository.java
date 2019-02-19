package com.pocopay.minibackoffice.repository;

import com.pocopay.minibackoffice.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
