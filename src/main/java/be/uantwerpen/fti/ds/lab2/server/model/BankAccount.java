package be.uantwerpen.fti.ds.lab2.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class BankAccount {

    @Id
    @GeneratedValue
    private Long id;

    private String accountNumber;

    private double balance;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "BANKACCOUNT_USER",
            joinColumns = {@JoinColumn(name = "bankaccount_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)}
    )
    Set<User> users;

    public BankAccount() {
        accountNumber = generateAccountNumber();
    }

    // https://www.quora.com/How-do-you-write-a-Java-program-to-generate-a-unique-bank-account-number
    public static String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
