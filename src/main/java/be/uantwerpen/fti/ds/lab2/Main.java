package be.uantwerpen.fti.ds.lab2;

import be.uantwerpen.fti.ds.lab2.client.BankingClient;
import be.uantwerpen.fti.ds.lab2.server.BankServerApplication;
import org.springframework.boot.SpringApplication;

public class Main {
    public static void main(String[] args) {
        BankServerApplication.main(args);
        new BankingClient("localhost", 8080, "ann@suantwerpen.be");
    }
}
