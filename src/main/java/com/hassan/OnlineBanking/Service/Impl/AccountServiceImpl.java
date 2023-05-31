package com.hassan.OnlineBanking.Service.Impl;

import com.hassan.OnlineBanking.Repository.PrimaryAccountRepo;
import com.hassan.OnlineBanking.Repository.SavingAccountRepo;
import com.hassan.OnlineBanking.Service.AccountService;
import com.hassan.OnlineBanking.Service.UserService;
import com.hassan.OnlineBanking.models.PrimaryAccount;
import com.hassan.OnlineBanking.models.SavingsAccount;

import com.hassan.OnlineBanking.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private PrimaryAccountRepo primaryAccountRepo;

    @Autowired
    private SavingAccountRepo savingAccountRepo;

    @Autowired
    private UserService userService;

    private static int nextAccountNumber = 11223145;


    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountGen());

        primaryAccountRepo.save(primaryAccount);

        return primaryAccountRepo.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    public SavingsAccount createSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountGen());

        savingAccountRepo.save(savingsAccount);

        return savingAccountRepo.findByAccountNumber(savingsAccount.getAccountNumber());
    }
    @Override
    public void deposit(String accountType, double amount, Principal principal) {

        User user=userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")){
            PrimaryAccount primaryAccount=user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepo.save(primaryAccount);

            Date date=new Date();
        }else if (accountType.equalsIgnoreCase("Savings")){
            SavingsAccount savingsAccount=user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingAccountRepo.save(savingsAccount);
            Date date=new Date();
        }
    }

    @Override
    public void withdraw(String accountType, double amount, Principal principal) {
        User user=userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")){
            PrimaryAccount primaryAccount=user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountRepo.save(primaryAccount);

        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount=user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingAccountRepo.save(savingsAccount);
            Date date=new Date();

        }

    }

    private int accountGen() {
        return ++nextAccountNumber;
    }
}
