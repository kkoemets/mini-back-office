package com.pocopay.minibackoffice.repository;

import com.pocopay.minibackoffice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Iterable<Transaction> findByReceiverAccountId(long id);

    Iterable<Transaction> findBySenderAccountId(long id);

    Transaction findTopByOrderByIdDesc();
}
