package be.uantwerpen.fti.ds.lab2.server.repository;

import be.uantwerpen.fti.ds.lab2.server.model.BankAccount;
import be.uantwerpen.fti.ds.lab2.server.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    <E> List<BankAccount> findByUsers(Set<User> users);

    BankAccount findByUsersAndAccountNumber(Set<User> users, String banknumber);

    boolean existsBankAccountByAccountNumberAndUsers(String accountNumber, Set<User> users);

    boolean existsByAccountNumber(String banknumber);

    @Modifying // required for @Query-statements that update, insert or delete entries.
    @Query("UPDATE BankAccount b SET b.balance = :newBalance WHERE b.accountNumber = :accountNumber")
    @Transactional // marks the method as transactional. When this method is invoked, others methods are suspended.
    void updateBalanceByAccountNumber(@Param("accountNumber") String accountNumber, @Param("newBalance") double newBalance);
}
