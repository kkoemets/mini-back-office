package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Iterable<Transaction> list();

    Transaction save(Transaction transaction);

    Optional<Transaction> getById(long id);

    Iterable<Transaction> saveAll(List<Transaction> transactions);

    Iterable<Transaction> getBySenderAccountId(long id);

    Iterable<Transaction> getByReceiverAccountId(long id);

    Long getLastTransactionsId();
}
