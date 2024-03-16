package be.uantwerpen.fti.ds.lab2.server.controller;

import be.uantwerpen.fti.ds.lab2.server.model.BankAccount;
import be.uantwerpen.fti.ds.lab2.server.model.dto.BankRequest;
import be.uantwerpen.fti.ds.lab2.server.model.User;
import be.uantwerpen.fti.ds.lab2.server.model.dto.UserViewBankAccountDTO;
import be.uantwerpen.fti.ds.lab2.server.repository.BankAccountRepository;
import be.uantwerpen.fti.ds.lab2.server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping
public class BankAccountController {

    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    UserRepository userRepository;

    private final ModelMapper modelmapper = new ModelMapper();

    @GetMapping("users/{userId}/bank-accounts")
    public List<UserViewBankAccountDTO> usersGetsBankAccounts(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Unknown");
        }
        List<BankAccount> userBankAccounts = bankAccountRepository.findByUsers(Set.of(user.get()));
        if (userBankAccounts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Accounts");
        }
        else {
            List<UserViewBankAccountDTO> userViewBankAccountDTOS = new ArrayList<>();
            for (BankAccount ba : userBankAccounts) {
                userViewBankAccountDTOS.add(modelmapper.map(ba, UserViewBankAccountDTO.class));
            }
            return userViewBankAccountDTOS;
        }

    }

    @GetMapping("users/{userId}/bank-accounts/{bank_number}")
    public UserViewBankAccountDTO userGetsAccount(@PathVariable Long userId, @PathVariable String bank_number) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Unknown");
        }
        if (!bankAccountRepository.existsBankAccountByAccountNumberAndUsers(bank_number, Set.of(user.get()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Unknown");
        }

        BankAccount userBankAccount = bankAccountRepository.findByUsersAndAccountNumber(Set.of(user.get()), bank_number);

        return modelmapper.map(userBankAccount, UserViewBankAccountDTO.class);


    }

    @PostMapping("users/{userId}/bank-accounts/{bank_number}/action")
    public ResponseEntity<String> userPerformsActionOnAccount(@PathVariable Long userId, @PathVariable String bank_number,
                                                              @RequestBody BankRequest bankRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Unknown");
        }
        if (!bankAccountRepository.existsByAccountNumber(bank_number)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Unknown");
        }

        BankAccount userBankAccount = bankAccountRepository.findByUsersAndAccountNumber(Set.of(user.get()), bank_number);

        if (bankRequest.getAction().equals("deposit")) {
            bankAccountRepository.updateBalanceByAccountNumber(bank_number, userBankAccount.getBalance() + bankRequest.getAmount());
        }
        else if (bankRequest.getAction().equals("withdraw")) {
            if (userBankAccount.getBalance() < bankRequest.getAmount()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
            }

            bankAccountRepository.updateBalanceByAccountNumber(bank_number, userBankAccount.getBalance() - bankRequest.getAmount());
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid action");
        }

        return ResponseEntity.ok(bankRequest.getAction() + " of " + bankRequest.getAmount() + " was successful");



    }

//    @PostMapping("users/{userId}/bank-accounts/{bank_number}/deposit")
//    public UserViewBankAccountDTO putAmountOnAccount(@PathVariable Long userId, @PathVariable String bank_number, @RequestParam("amount") double amount) {
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Unknown");
//        }
//
//        BankAccount userBankAccount = bankAccountRepository.findByUsersAndAccountNumber(Set.of(user.get()), bank_number);
//
//        bankAccountRepository.updateBalanceByAccountNumber(bank_number, userBankAccount.getBalance() + amount);
//
//        userBankAccount = bankAccountRepository.findByUsersAndAccountNumber(Set.of(user.get()), bank_number);
//
//        return modelmapper.map(userBankAccount, UserViewBankAccountDTO.class);
//
//
//    }
//
//    @PostMapping("users/{userId}/bank-accounts/{bank_number}/withdraw")
//    public UserViewBankAccountDTO getAmountFromAccount(@PathVariable Long userId, @PathVariable String bank_number, @RequestParam("amount") double amount) {
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Unknown");
//        }
//
//        BankAccount userBankAccount = bankAccountRepository.findByUsersAndAccountNumber(Set.of(user.get()), bank_number);
//        if (userBankAccount.getBalance() < amount) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
//        }
//
//        bankAccountRepository.updateBalanceByAccountNumber(bank_number, userBankAccount.getBalance()-amount);
//
//        userBankAccount = bankAccountRepository.findByUsersAndAccountNumber(Set.of(user.get()), bank_number);
//
//        return modelmapper.map(userBankAccount, UserViewBankAccountDTO.class);
//
//
//    }


}
