package be.uantwerpen.fti.ds.lab2.server;

import be.uantwerpen.fti.ds.lab2.client.BankingClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankServerApplication.class, args);
//        new BankingClient("localhost", 8080, "ann@suantwerpen.be");

    }

}
