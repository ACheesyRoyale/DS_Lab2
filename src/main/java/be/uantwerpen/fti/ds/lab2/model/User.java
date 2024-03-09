package be.uantwerpen.fti.ds.lab2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;
    private String firstName;
    private String lastName;

    public User() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@gmail.com";

    }

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
