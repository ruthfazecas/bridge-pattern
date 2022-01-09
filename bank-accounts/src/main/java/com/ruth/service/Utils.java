package com.ruth.service;

import com.ruth.Constants;
import com.ruth.domain.BankAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {
    final static Random random = new Random();

    public static Map<Long, BankAccount> createBankAccounts() {
        Map<Long, BankAccount> bankAccounts = new HashMap<>();

        for (int i = 0; i < Constants.NO_BANK_ACCOUNTS; i++) {
            BankAccount bankAccount = new BankAccount(random.nextDouble() * 10000);
            bankAccounts.put(bankAccount.getSerialNumber(), bankAccount);
        }
        System.out.println("Finished creating " + Constants.NO_BANK_ACCOUNTS + " bank accounts.");
        return bankAccounts;
    }

    public static long generateRandom(List<Long> exclusions) {
        long generatedNumber = random.nextLong(Constants.NO_BANK_ACCOUNTS);
        while (exclusions.contains(generatedNumber))
            generatedNumber = random.nextLong(Constants.NO_BANK_ACCOUNTS);
        return generatedNumber;
    }
}
