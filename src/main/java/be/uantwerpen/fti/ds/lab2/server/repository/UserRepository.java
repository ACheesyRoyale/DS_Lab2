package be.uantwerpen.fti.ds.lab2.server.repository;

import be.uantwerpen.fti.ds.lab2.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
