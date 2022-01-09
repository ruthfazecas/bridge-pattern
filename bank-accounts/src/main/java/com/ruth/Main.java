package com.ruth;

import com.ruth.service.BankService;
import com.ruth.service.Utils;

public class Main {
    public static void main(String[] args) {
        // run multiple times to get different behaviours (the transactions are random)
        BankService bankService = new BankService(Utils.createBankAccounts());
        bankService.simulateIndividualTransfers();
        bankService.simulateBatchTransfers();
    }
}
