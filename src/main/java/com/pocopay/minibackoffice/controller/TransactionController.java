package com.pocopay.minibackoffice.controller;

import com.pocopay.minibackoffice.model.Account;
import com.pocopay.minibackoffice.model.Transaction;
import com.pocopay.minibackoffice.service.AccountService;
import com.pocopay.minibackoffice.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    Logger log = LoggerFactory.getLogger(TransactionController.class);

    private TransactionService transactionService;

    private AccountService accountService;

    private final Set<String> validKeys = new HashSet<>(Arrays.asList("senderAccountName", "receiverAccountName",
            "amount", "description"));

    public TransactionController(TransactionService transactionService,
                                 AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @ApiOperation(value = "Returns all transactions as a list")
    @GetMapping(value = {"", "/"})
    public Iterable<Transaction> list() {
        return transactionService.list();
    }

    @ApiOperation(value = "Returns a transaction by ID")
    @GetMapping(value = "/{id}")
    public Transaction getTransaction(@PathVariable long id) {
        Optional<Transaction> optionalTransaction = transactionService.getById(id);

        return optionalTransaction.orElse(new Transaction());
    }

    @ApiOperation(value = "Returns transactions as a list by sender's account ID")
    @GetMapping(value = "/account/sender/{id}")
    public Iterable<Transaction> getTransactionBySenderAccountId(@PathVariable long id) {
        Optional<Account> optionalAccount = accountService.findById(id);

        return optionalAccount
                .map(Account::getTransactionsAsSender)
                .orElse(new HashSet<>());
    }

    @ApiOperation(value = "Returns transactions as a list by receiver's account ID")
    @GetMapping(value = "/account/receiver/{id}")
    public Iterable<Transaction> getTransactionByReceiverAccountId(@PathVariable long id) {
        Optional<Account> optionalAccount = accountService.findById(id);

        return optionalAccount
                .map(Account::getTransactionsAsReceiver)
                .orElse(new HashSet<>());
    }

    @ApiOperation(value = "Creates a transaction between two accounts")
    @CrossOrigin
    @PostMapping(value = "/create")
    public ResponseEntity<String> createTransaction(@RequestBody Map<String, String> json) {
        long requestId = transactionService.getLastTransactionsId() + 1;
        log.info("Request from client to create a new transaction ID#" + requestId);
        Transaction transaction;
        try {
            if (json == null) {
                throw new IllegalArgumentException("Received JSON that was null");
            }

            final Map<String, String> request = json;
            if (!hasRequestValidKeys(request)) {
                throw new IllegalArgumentException("Invalid keys set in JSON");
            }

            final String requestedSenderAccountName = request.get("senderAccountName");
            final String requestedReceiverAccountName = request.get("receiverAccountName");
            final String requestedAmount = request.get("amount");
            final String requestedDesc = request.get("description");

            final Account senderAccount = getAccountByName(requestedSenderAccountName);

            final Account receiverAccount = getAccountByName(requestedReceiverAccountName);

            if (requestedDesc.isEmpty()) {
                throw new IllegalArgumentException("Received JSON has empty description value");
            }

            final BigDecimal amount = new BigDecimal(requestedAmount);

            if (isNegative(amount)) {
                throw new IllegalArgumentException("Amount cannot be negative");
            }

            exchangeAmountBetweenAccounts(senderAccount, receiverAccount, amount);

            log.info("Creating transaction ID#" + requestId +": " + senderAccount.getName() + "[id:" +
                    senderAccount.getId() + "] -> " + receiverAccount.getName() + "[id:" +
                    receiverAccount.getId() + "] [Amount: " + amount.toPlainString() +
                    "] [Description: " + requestedDesc + "]");

            Transaction newTransaction = new Transaction(senderAccount, receiverAccount,
                    new Date(), requestedDesc, amount.doubleValue());

            transaction = transactionService.save(newTransaction);

            addTransactionToAccounts(transaction, senderAccount, receiverAccount);

            log.info("New transaction created: ID#" + transaction.getId());

            accountService.save(senderAccount);
            accountService.save(receiverAccount);
        } catch (Exception e) {
            String msg;
            if (e instanceof IllegalArgumentException || e instanceof NumberFormatException) {
                msg = e.getMessage();
            } else {
                msg = "Something went wrong on the server side!";
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpStatus.OK).body("ID#" + transaction.getId());
    }

    private Account getAccountByName(String requestedSenderAccountName)
            throws IllegalArgumentException {
        return accountService
                .findAccountByName(requestedSenderAccountName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Account '" + requestedSenderAccountName + "' does not exist"));
    }

    private void addTransactionToAccounts(Transaction transaction, Account senderAccount, Account receiverAccount) {
        senderAccount.addTransactionAsSender(transaction);
        receiverAccount.addTransactionAsReceiver(transaction);
    }

    private void exchangeAmountBetweenAccounts(Account senderAccount, Account receiverAccount, BigDecimal amount) {
        senderAccount.decreaseBalance(amount);
        receiverAccount.increaseBalance(amount);
    }

    private boolean isNegative(BigDecimal amount) {
        return amount.doubleValue() < 0;
    }

    private boolean hasRequestValidKeys(Map<String, String> request) {
        if (request == null) return false;

        for (String key : request.keySet()) {
            if (!validKeys.contains(key)) return false;
        }

        return true;
    }

}
