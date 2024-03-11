package be.uantwerpen.fti.ds.lab2.server.repository;

import be.uantwerpen.fti.ds.lab2.server.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
