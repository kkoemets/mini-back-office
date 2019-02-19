package com.pocopay.minibackoffice.service;

import com.pocopay.minibackoffice.model.Transaction;
import com.pocopay.minibackoffice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Iterable<Transaction> list() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getById(long id) {
        Optional<Transaction> result = transactionRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public Iterable<Transaction> saveAll(List<Transaction> transactions) {
        return transactionRepository.saveAll(transactions);
    }

    @Override
    public Iterable<Transaction> getBySenderAccountId(long id) {
        return transactionRepository.findBySenderAccountId(id);
    }

    @Override
    public Iterable<Transaction> getByReceiverAccountId(long id) {
        return transactionRepository.findByReceiverAccountId(id);
    }
}
