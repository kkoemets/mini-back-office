package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Account;
import com.pocopay.minibackoffice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Iterable<Account> list() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Iterable<Account> saveAll(List<Account> accounts) {
        return accountRepository.saveAll(accounts);
    }

    @Override
    public Optional<Iterable<Account>> findByCustomerId(long id) {
        return Optional.ofNullable(accountRepository.findByCustomerId(id));
    }

    @Override
    public Optional<Account> findAccountByName(String name) {
        return Optional.ofNullable(accountRepository.findAccountByName(name));
    }

}
