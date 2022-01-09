package com.ruth.service;

import com.ruth.Constants;
import com.ruth.domain.BankAccount;
import com.ruth.domain.Transfer;
import com.ruth.history.BankAccountsHistoryCaretaker;
import com.ruth.history.BankAccountsMemento;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


import static com.ruth.service.Utils.generateRandom;

public class BankService {
    private final Map<Long, BankAccount> bankAccounts;

    private final BankAccountsHistoryCaretaker bankAccountsHistoryCaretaker = new BankAccountsHistoryCaretaker();

    public BankService(Map<Long, BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public void simulateBatchTransfers() {
        Random random = new Random();

        for (int i = 0; i < Constants.NO_BATCHES; i++) {
            List<Transfer> transfers = new ArrayList<>();
            for (int j = 0; j < Constants.NO_TRANSFERS_PER_BATCH; j++) {
                long sourceSerialNumber = generateRandom(List.of());
                long destinationSerialNumber = generateRandom(List.of(sourceSerialNumber));
                Transfer transfer = new Transfer(sourceSerialNumber, destinationSerialNumber, BigDecimal.valueOf(random.nextDouble() * 5000));

                transfers.add(transfer);
            }
            performBatch(transfers);
        }
    }

    private void performBatch(List<Transfer> transfersInBatch) {
        System.out.println("-------------------Starting to execute Batch: " + transfersInBatch);
        bankAccountsHistoryCaretaker.addMemento(createMemento(
            transfersInBatch.stream().map(Transfer::getSerialNumber).collect(Collectors.toList()))
        );

        for (Transfer transfer : transfersInBatch) {
            BankAccount sourceBankAccount = bankAccounts.get(transfer.getSourceSerialNumber());
            BankAccount destinationBankAccount = bankAccounts.get(transfer.getDestinationSerialNumber());

            try {
                sourceBankAccount.handleTransfer(transfer);
                destinationBankAccount.handleTransfer(transfer);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                System.out.println("-------------------Exception in transfer. Performing rollback");
                restoreFromMemento(bankAccountsHistoryCaretaker.getMemento());
                return;
            }
        }
        System.out.println("-------------------Finished to execute Batch without rollback");
    }

    public void simulateIndividualTransfers() {
        Random random = new Random();
        List<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < Constants.NO_INDIVIDUAL_TRANSFERS; i++) {
            long sourceSerialNumber = generateRandom(List.of());
            long destinationSerialNumber = generateRandom(List.of(sourceSerialNumber));

            Transfer transfer = new Transfer(sourceSerialNumber, destinationSerialNumber, BigDecimal.valueOf(random.nextDouble() * 10000));
            BankAccount sourceBankAccount = bankAccounts.get(sourceSerialNumber);
            BankAccount destinationBankAccount = bankAccounts.get(destinationSerialNumber);

            tasks.add(() -> sourceBankAccount.handleTransfer(transfer));
            tasks.add(() -> destinationBankAccount.handleTransfer(transfer));

        }
        System.out.println("Start executing individual transactions");
        tasks.forEach(task -> {
            try {
                task.run();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    public BankAccountsMemento createMemento(List<Long> bankAccountsAffectedIds) {
        Map<Long, BankAccount> bankAccountsAffected = new HashMap<>();
        bankAccounts.entrySet()
            .stream().filter(entry -> bankAccountsAffectedIds.contains(entry.getKey()))
            .forEach(entry -> {
                bankAccountsAffected.put(entry.getKey(), BankAccount.cloneBankAccount(entry.getValue()));
            });
        return new BankAccountsMemento(bankAccountsAffected);
    }

    public void restoreFromMemento(BankAccountsMemento save) {
        bankAccounts.putAll(save.getBankAccounts());
    }

    @SneakyThrows
    private void logOperations() {
        StringBuffer output = new StringBuffer();
        Files.writeString(Path.of("logs.txt"), bankAccounts.values().stream()
            .map(BankAccount::getWriteToFileString)
            .reduce(output, StringBuffer::append));
    }
}
