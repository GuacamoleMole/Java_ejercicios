package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private double totalBalance;
    private final String idAccount;
    private final Lock lock = new ReentrantLock();

    public BankAccount(String idAccount, double initialBalance) {
        this.idAccount = idAccount;
        this.totalBalance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }

        lock.lock();
        try {
            totalBalance += amount;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }

        lock.lock();
        try {
            if (totalBalance < amount) {
                return false;
            }
            totalBalance -= amount;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean transferTo(BankAccount destination, double amount) {
        if (destination == null || destination == this) {
            throw new IllegalArgumentException("Cuenta destino no puede ser null o la propia cuenta");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }

        BankAccount first = System.identityHashCode(this) <
                System.identityHashCode(destination) ? this : destination;
        BankAccount second = first == this ? destination : this;

        first.lock.lock();
        try {
            second.lock.lock();
            try {
                if (this.totalBalance < amount) {
                    return false;
                }

                this.totalBalance -= amount;
                destination.totalBalance += amount;
                return true;

            } finally {
                second.lock.unlock();
            }
        } finally {
            first.lock.unlock();
        }
    }


    public double getBalance() {
        return totalBalance;
    }

    public String getIdAccount() {
        return idAccount;
    }
}
