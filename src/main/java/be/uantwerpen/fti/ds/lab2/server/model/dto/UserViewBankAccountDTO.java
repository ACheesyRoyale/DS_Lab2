package be.uantwerpen.fti.ds.lab2.server.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserViewBankAccountDTO {
    private String accountNumber;

    private double balance;

    public UserViewBankAccountDTO() {}

}
