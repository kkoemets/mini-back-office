package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Account;

import java.util.List;

public interface AccountService {

    Iterable<Account> list();

    Account save(Account account);

    Account findById(long id);

    Iterable<Account> saveAll(List<Account> accounts);

    Iterable<Account> findByCustomerId(long id);

    Account findAccountByName(String name);
}
