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
    public Account findById(long id) {
        Optional<Account> result = accountRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public Iterable<Account> saveAll(List<Account> accounts) {
        return accountRepository.saveAll(accounts);
    }

    @Override
    public Iterable<Account> findByCustomerId(long id) {
        return accountRepository.findByCustomerId(id);
    }

    @Override
    public Account findAccountByName(String name) {
        return accountRepository.findAccountByName(name);
    }

}
