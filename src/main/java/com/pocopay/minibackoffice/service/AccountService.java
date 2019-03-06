package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Iterable<Account> list();

    Account save(Account account);

    Optional<Account> findById(long id);

    Iterable<Account> saveAll(List<Account> accounts);

    Optional<Iterable<Account>> findByCustomerId(long id);

    Optional<Account> findAccountByName(String name);
}
