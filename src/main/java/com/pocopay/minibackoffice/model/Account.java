package com.pocopay.minibackoffice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private BigDecimal balance;

    @ToString.Exclude
    @ManyToOne
    @NotNull
    @JoinColumn
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactionsAsSender;

    @JsonIgnore
    @OneToMany(mappedBy = "receiverAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactionsAsReceiver;

    public Account() {
    }

    // for creating dummy transactions
    public Account(String name, Double balance) {
        this.name = name;
        this.balance = BigDecimal.valueOf(balance).setScale(2, RoundingMode.HALF_EVEN);
        this.customer = null;
        this.transactionsAsSender = new LinkedHashSet<>();
        this.transactionsAsReceiver = new LinkedHashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        return name != null ? name.equals(account.name) : account.name == null;
    }

    // https://stackoverflow.com/questions/48324474/cascading-entity-save-with-composite-key-throws-nullpointerexception
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void addTransactionAsSender(Transaction transaction) {
        transactionsAsSender.add(transaction);
    }

    public void addTransactionAsReceiver(Transaction transaction) {
        transactionsAsReceiver.add(transaction);
    }
}
