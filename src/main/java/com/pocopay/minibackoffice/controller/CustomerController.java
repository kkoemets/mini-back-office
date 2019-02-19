package com.pocopay.minibackoffice.controller;

import com.pocopay.minibackoffice.model.Customer;
import com.pocopay.minibackoffice.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Returns all customers as a list")
    @GetMapping(value = {"", "/"})
    public Iterable<Customer> list() {
        return customerService.list();
    }

    @ApiOperation(value = "Returns a customer by ID")
    @GetMapping("/{id}")
    public Customer getCustomerDetails(@PathVariable long id) {
        return customerService.findById(id);
    }
}
