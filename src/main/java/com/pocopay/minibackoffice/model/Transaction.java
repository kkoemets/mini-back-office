package com.pocopay.minibackoffice.model;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date transactionDate;

    private String description;

    private BigDecimal amount;

    //    @JsonIgnore
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn
    private Account senderAccount;

    //    @JsonIgnore
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn
    private Account receiverAccount;

    public Transaction() {

    }

    public Transaction(Account senderAccount, Account ReceiverAccount, Date transactionDate,
                       String description, double amount) {
        this.senderAccount = senderAccount;
        this.receiverAccount = ReceiverAccount;
        this.description = description;
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_EVEN);
        this.transactionDate = transactionDate;
    }

    // for creating dummy transactionsAsSender
    public Transaction(Long id, Account senderAccount, Account receiverAccount, Date transactionDate,
                       String description, double amount) {
        this(senderAccount, receiverAccount, transactionDate, description, amount);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
