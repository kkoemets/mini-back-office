package com.pocopay.minibackoffice.runner;

import com.github.javafaker.Faker;
import com.pocopay.minibackoffice.model.Account;
import com.pocopay.minibackoffice.model.Customer;
import com.pocopay.minibackoffice.model.Transaction;
import com.pocopay.minibackoffice.service.AccountService;
import com.pocopay.minibackoffice.service.CustomerService;
import com.pocopay.minibackoffice.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;


/**
 * Create Dummy Data into Database
 */
@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    private Logger log = LoggerFactory.getLogger(CommandLineRunnerBean.class);
    private Random random = new Random();
    private Faker faker = new Faker();

    @Override
    public void run(String... args) {
        // Activation - TRUE/FALSE
        if (false) return;
        // Configure
        int amountDummyCustomers = 24;
        int amountDummyAccounts = 98;
        int amountDummyTransactions = 401;
        //
        List<Customer> customers = createCustomers(amountDummyCustomers);
        log.info(customers.size() + " dummy customers created");
        List<Account> accounts = createAccounts(amountDummyAccounts);
        log.info(accounts.size() + " dummy accounts created");

        addAccountsToCustomers(accounts, customers);

        List<Transaction> transactions = createDummyTransactions(amountDummyTransactions);
        log.info(transactions.size() + " dummy transactions created");

        addAccountsToTransactions(accounts, transactions);

        transactionService.saveAll(transactions);
        accountService.saveAll(accounts);
        customerService.saveAll(customers);
    }

    private List<Customer> createCustomers(int amount) {
        List<Customer> customers = new ArrayList<>();
        for (long i = 1; i <= amount; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = firstName + "." + lastName + "@mail.com";
            String phone = faker.phoneNumber().cellPhone();
            Customer dummyCustomer = new Customer(i, firstName, lastName, email, phone);
            customers.add(dummyCustomer);
        }
        return customers;
    }

    private List<Account> createAccounts(int amount) {
        List<Account> accounts = new ArrayList<>();
        HashSet<String> set = new HashSet<>(); // unique in database
        for (long i = 1; i <= amount; i++) {
            String username;
            username = getUsername();
            while (set.contains(username)) {
                username = username + '1';
            }
            accounts.add(new Account(i, username, 500.));
            set.add(username);
        }
        return accounts;
    }

    private String getUsername() {
        switch (random.nextInt(5)){
            case 0:
                return faker.gameOfThrones().character();
            case 1:
                return faker.cat().name();
            case 2:
                return faker.artist().name();
            case 3:
                return faker.twinPeaks().character();
            default:
                return faker.esports().player();
        }
    }

    private void addAccountsToCustomers(List<Account> accounts, List<Customer> customers) {
        for (Account account : accounts) {
            Customer customer = customers.get(random.nextInt(customers.size()));
            customer.addAccount(account);
            account.setCustomer(customer);
        }
    }

    private List<Transaction> createDummyTransactions(int amount) {
        List<Transaction> transactions = new ArrayList<>();
        for (long id = 1; id <= amount; id++) {


            Date date = new Date(System.currentTimeMillis() - random.nextInt(432000000));

            Transaction transaction = new Transaction(
                    id, null, null, date
                    , faker.lorem().sentence(3), 0.
            );
            transactions.add(transaction);
        }
        return transactions;
    }

    private void addAccountsToTransactions(List<Account> accounts, List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            Account sender = accounts.get(random.nextInt(accounts.size()));
            Account receiver = accounts.get(random.nextInt(accounts.size()));
            transaction.setSenderAccount(sender);
            sender.addTransactionAsSender(transaction);
            transaction.setReceiverAccount(receiver);
            receiver.addTransactionAsReceiver(transaction);

            // transaction amount = senderBalance * randomPercent[0-1)
            BigDecimal transactionAmount = sender.getBalance()
                    .multiply(BigDecimal.valueOf(Math.random()));

            sender.setBalance(sender.getBalance().subtract(transactionAmount));
            receiver.setBalance(receiver.getBalance().add(transactionAmount));
            transaction.setAmount(transactionAmount);
        }
    }
}