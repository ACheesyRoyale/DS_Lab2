package be.uantwerpen.fti.ds.lab2.server.controller;

import be.uantwerpen.fti.ds.lab2.server.model.User;
import be.uantwerpen.fti.ds.lab2.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public Iterable<User> getUsers() {
        return ((List<User>)userRepository.findAll())
                .stream().toList();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userRepository.save(user);

    }
}
