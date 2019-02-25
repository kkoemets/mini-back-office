package com.pocopay.minibackoffice.controller;

import com.pocopay.minibackoffice.model.Account;
import com.pocopay.minibackoffice.model.Transaction;
import com.pocopay.minibackoffice.service.AccountService;
import com.pocopay.minibackoffice.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    Logger log = LoggerFactory.getLogger(TransactionController.class);

    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "Returns all transactions as a list")
    @GetMapping(value = {"", "/"})
    public Iterable<Transaction> list() {
        return transactionService.list();
    }

    @ApiOperation(value = "Returns a transaction by ID")
    @GetMapping(value = "/{id}")
    public Transaction getTransaction(@PathVariable long id) {
        return transactionService.getById(id);
    }

    @ApiOperation(value = "Returns transactions as a list by sender's account ID")
    @GetMapping(value = "/account/sender/{id}")
    public Iterable<Transaction> getTransactionBySenderAccountId(@PathVariable long id) {
        return accountService.findById(id).getTransactionsAsSender();
    }

    @ApiOperation(value = "Returns transactions as a list by receiver's account ID")
    @GetMapping(value = "/account/receiver/{id}")
    public Iterable<Transaction> getTransactionByReceiverAccountId(@PathVariable long id) {
        return accountService.findById(id).getTransactionsAsReceiver();
    }

    @ApiOperation(value = "Creates a transaction between two accounts")
    @CrossOrigin //TODO!! https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
    @PostMapping(value = "/create")
    public ResponseEntity<String> createTransaction(@RequestBody Map<String, String> json) {
        // check if client sends correct json
        Transaction transaction;
        try {
            String[] validKeys = {"senderAccountName", "receiverAccountName", "amount",
                    "description"};
            if (!isValidNewTransactionJson(json, validKeys)) {
                throw new IllegalArgumentException("Wrong JSON format for creating a new Transaction");
            }
            //
            Account sender = getAccount(json, "senderAccountName");
            Account receiver = getAccount(json, "receiverAccountName");
            String desc = json.get("description");
            //
            double amount;
            try {
                amount = Double.valueOf(json.get("amount"));
                if (!isValidAmount(amount)) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Client didn't send a valid number as an amount in " +
                        "Transaction" + json.get("amount"));
            }
            sender.setBalance(sender.getBalance().subtract(BigDecimal.valueOf(amount)));
            receiver.setBalance(receiver.getBalance().add(BigDecimal.valueOf(amount)));
            transaction = new Transaction(sender, receiver, new Date(), desc, amount);
            transaction = transactionService.save(transaction);
            sender.addTransactionAsSender(transaction);
            receiver.addTransactionAsReceiver(transaction);
            log.info("New transaction created: " + transaction.getId());
            accountService.save(sender);
            accountService.save(receiver);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("ID#" +
                transaction.getId());
    }

    private boolean isValidAmount(double amount) throws NumberFormatException {
        return (amount > 0);
    }

    private Account getAccount(Map<String, String> json, String key) throws IllegalArgumentException {
        String name = json.get(key);
        Account sender = accountService.findAccountByName(name);
        if (sender == null) throw new IllegalArgumentException("Account with name '" +
                name + "' does not exist!");
        return sender;
    }

    private boolean isValidNewTransactionJson(Map<String, String> json, String[] validKeys) {
        if (json.size() != validKeys.length) return false;

        for (String key : validKeys) {
            if (!json.containsKey(key)) return false;
        }
        return true;
    }
}
