package com.pocopay.minibackoffice.repository;

import com.pocopay.minibackoffice.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Iterable<Account> findByCustomerId(long id);

    Account findAccountByName(String name);
}
