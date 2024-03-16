package be.uantwerpen.fti.ds.lab2.server.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankRequest {
    private String action;

    private double amount;

    private String destinationAccountNumber;

    public BankRequest(){}
}
