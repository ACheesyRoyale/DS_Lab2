package be.uantwerpen.fti.ds.lab2.repository;

import be.uantwerpen.fti.ds.lab2.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
