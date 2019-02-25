package com.pocopay.minibackoffice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ",
            locale = "en_GB", timezone = "GMT+2")
    private Date transactionDate;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal amount;

    @ManyToOne
    @NotNull
    @JoinColumn
    private Account senderAccount;

    @ManyToOne
    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
