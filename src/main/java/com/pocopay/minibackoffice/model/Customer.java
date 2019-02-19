package com.pocopay.minibackoffice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;


@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 65)
    private String firstName;

    @NotNull
    @Size(max = 65)
    private String lastName;

    @NotNull
    @Email
    @Column(unique = true)
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 20)
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id")
    private Set<Account> accounts;

    public Customer() {
    }

    // for creating dummy customers
    public Customer(Long id, @NotNull @Size(max = 65) String firstName,
                    @NotNull @Size(max = 65) String lastName,
                    @NotNull @Email @Size(max = 100) String email,
                    @NotNull @Size(max = 20) String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.accounts = new LinkedHashSet<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}
