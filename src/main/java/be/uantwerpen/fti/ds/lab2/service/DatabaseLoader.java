package be.uantwerpen.fti.ds.lab2.service;

import be.uantwerpen.fti.ds.lab2.model.BankAccount;
import be.uantwerpen.fti.ds.lab2.model.User;
import be.uantwerpen.fti.ds.lab2.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DatabaseLoader {
    @Autowired
    final private UserRepository userRepository;

    public DatabaseLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void initDatabase() {
        User u1 = new User("ann@suantwerpen.be", "ann", "peeters");
        User u2 = new User("ben@suantwerpen.be", "ben", "mertens");

        BankAccount b1 = new BankAccount();
        b1.setUsers(Set.of(u1));

        userRepository.save(u1);
        userRepository.save(u2);
    }

}
