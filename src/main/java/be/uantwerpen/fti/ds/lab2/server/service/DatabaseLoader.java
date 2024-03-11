package be.uantwerpen.fti.ds.lab2.server.service;

import be.uantwerpen.fti.ds.lab2.server.model.BankAccount;
import be.uantwerpen.fti.ds.lab2.server.model.User;
import be.uantwerpen.fti.ds.lab2.server.repository.BankAccountRepository;
import be.uantwerpen.fti.ds.lab2.server.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DatabaseLoader {
    @Autowired
    final private UserRepository userRepository;

    @Autowired
    final private BankAccountRepository bankAccountRepository;

    public DatabaseLoader(UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @PostConstruct
    private void initDatabase() {
        User u1 = new User("ann@suantwerpen.be", "ann", "peeters");
        User u2 = new User("ben@suantwerpen.be", "ben", "mertens");

        BankAccount b1 = new BankAccount();
        BankAccount b2 = new BankAccount();
        BankAccount b3 = new BankAccount();

        b1.setUsers(Set.of(u1, u2));
        b2.setUsers(Set.of(u2));
        b3.setUsers(Set.of(u1));


        userRepository.save(u1);
        userRepository.save(u2);

        bankAccountRepository.save(b1);
        bankAccountRepository.save(b2);
        bankAccountRepository.save(b3);


    }

}
