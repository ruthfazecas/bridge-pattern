package com.ruth.history;

import java.util.ArrayDeque;
import java.util.Deque;

public class BankAccountsHistoryCaretaker {
    private final Deque<BankAccountsMemento> history = new ArrayDeque<>();

    public void addMemento(BankAccountsMemento memento) {
        history.addLast(memento);
    }

    public BankAccountsMemento getMemento() {
        if (history.isEmpty()) {
            throw new RuntimeException("The history stack is empty");
        }
        return history.pollLast();
    }
}
