package com.pocopay.minibackoffice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true)
    @Size(max = 50)
    private String name;

    private BigDecimal balance;

    @JsonIgnore
    @ManyToOne
    // https://www.concretepage.com/hibernate/example-notfound-hibernate
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn
    private Customer customer;

    @JsonIgnore
    // https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactionsAsSender;

    @JsonIgnore
    @OneToMany(mappedBy = "receiverAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactionsAsReceiver;

    public Account() {
    }

    // for creating dummy transactionsAsSender
    public Account(Long id, @Size(max = 50) String name, Double balance) {
        this.id = id;
        this.name = name;
        this.balance = BigDecimal.valueOf(balance).setScale(2, RoundingMode.HALF_EVEN);
        this.transactionsAsSender = new LinkedHashSet<>();
        this.transactionsAsReceiver = new LinkedHashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (!id.equals(account.id)) return false;
        return name.equals(account.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public void addTransactionAsSender(Transaction transaction) {
        transactionsAsSender.add(transaction);
    }
    public void addTransactionAsReceiver(Transaction transaction) {
        transactionsAsReceiver.add(transaction);
    }
}
