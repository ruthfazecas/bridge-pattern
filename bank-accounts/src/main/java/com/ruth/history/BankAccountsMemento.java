package com.ruth.history;

import com.ruth.domain.BankAccount;

import java.util.Map;

public record BankAccountsMemento(Map<Long, BankAccount> bankAccounts) {
    public Map<Long, BankAccount> getBankAccounts() {
        return bankAccounts;
    }
}
