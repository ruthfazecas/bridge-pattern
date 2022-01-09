package com.ruth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;

@Data
@AllArgsConstructor
public class BankAccount {
    private long serialNumber = currentStaticSerialNumber.incrementAndGet();
    private static final AtomicLong currentStaticSerialNumber = new AtomicLong(-1);

    private BigDecimal balance;
    private Map<Long, Transfer> transfersLog = new HashMap<>();

    public BankAccount(double initialBalance) {
        this.balance = valueOf(initialBalance);
    }

    public void handleTransfer(Transfer transfer) {
        if (serialNumber == transfer.getSourceSerialNumber()) {
            if (balance.compareTo(transfer.getAmount()) < 0) {
                throw new RuntimeException("Cannot perform transfer. Not enough money.");
            }
            balance = balance.subtract(transfer.getAmount());
        }
        if (serialNumber == transfer.getDestinationSerialNumber()) {
            balance = balance.add(transfer.getAmount());
        }
        transfersLog.put(transfer.getSerialNumber(), transfer);
    }

    public void withdraw(double amount) {
        if (balance.compareTo(valueOf(amount)) < 0) {
            throw new RuntimeException("Cannot perform withdraw. Not enough money.");
        }
        balance = balance.subtract(valueOf(amount));
    }

    public void deposit(double amount) {
        balance = balance.add(valueOf(amount));
    }

    public static BankAccount cloneBankAccount(BankAccount bankAccount) {
        return new BankAccount(bankAccount.serialNumber, bankAccount.getBalance(), bankAccount.getTransfersLog());
    }

    public StringBuffer getWriteToFileString() {
        StringBuffer bankAccountInfo = new StringBuffer();
        bankAccountInfo.append(format("BankAccount: serialNumber: %d, balance: %s, transfersLog: \n", serialNumber, balance));
        transfersLog.forEach(
            (id, transfer) -> bankAccountInfo.append("\t").append(transfer.toString()).append("\n")
        );
        return bankAccountInfo;
    }
}
