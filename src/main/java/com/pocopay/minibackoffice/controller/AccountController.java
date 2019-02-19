package com.pocopay.minibackoffice.controller;

import com.pocopay.minibackoffice.model.Account;
import com.pocopay.minibackoffice.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Returns all accounts as a list")
    @GetMapping(value = {"", "/"})
    public Iterable<Account> list() {
        return accountService.list();
    }

    @ApiOperation(value = "Returns all accounts by customer ID")
    @GetMapping("/customer/{id}")
    public Iterable<Account> getAccountByCustomer(@PathVariable long id) {
        return accountService.findByCustomerId(id);
    }
}
