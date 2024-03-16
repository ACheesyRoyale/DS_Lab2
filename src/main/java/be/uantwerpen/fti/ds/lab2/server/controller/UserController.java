package be.uantwerpen.fti.ds.lab2.server.controller;

import be.uantwerpen.fti.ds.lab2.server.model.User;
import be.uantwerpen.fti.ds.lab2.server.model.dto.UserLoginDTO;
import be.uantwerpen.fti.ds.lab2.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return ((List<User>)userRepository.findAll())
                .stream().toList();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

//    @PostMapping("/users")
//    public void addUser(@RequestBody User user) {
//        userRepository.save(user);
//
//    }

    @PostMapping("/login")
    public Optional<Integer> getIdByUser(@RequestBody UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findByEmail(userLoginDTO.getEmail());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Unknown");
        }
        return user.map(User::getId);
    }
}
