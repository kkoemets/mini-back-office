package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Transaction;

import java.util.List;

public interface TransactionService {

    Iterable<Transaction> list();

    Transaction save(Transaction transaction);

    Transaction getById(long id);

    Iterable<Transaction> saveAll(List<Transaction> transactions);

    Iterable<Transaction> getBySenderAccountId(long id);

    Iterable<Transaction> getByReceiverAccountId(long id);
}
