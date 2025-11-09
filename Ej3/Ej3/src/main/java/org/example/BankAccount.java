package org.example;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private final AtomicReference<Double> balance;
    private final String idAccount;
    private final Lock lock = new ReentrantLock();

    public BankAccount(String idAccount, double initialBalance) {
        this.idAccount = idAccount;
        this.balance = new AtomicReference<>(initialBalance);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        balance.updateAndGet(current -> current + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        Double oldValue;
        Double newValue;
        do {
            oldValue = balance.get();
            if (oldValue < amount) {
                return false;
            }
            newValue = oldValue - amount;
        } while (!balance.compareAndSet(oldValue, newValue));

        return true;
    }

    public boolean transferTo(BankAccount destination, double amount) {
        if (destination == null || amount <= 0) {
            throw new IllegalArgumentException("Destino inválido o monto no positivo");
        }
        if (this == destination) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (this.getBalance() < amount) {
            return false;
        }

        BankAccount first = System.identityHashCode(this) <
                System.identityHashCode(destination) ? this : destination;
        BankAccount second = first == this ? destination : this;

        first.lock.lock();
        try {
            second.lock.lock();
            try {
                Double sourceBalance = this.balance.get();
                if (sourceBalance < amount) {
                    return false;
                }

                this.balance.updateAndGet(current -> current - amount);
                destination.balance.updateAndGet(current -> current + amount);
                return true;

            } finally {
                second.lock.unlock();
            }
        } finally {
            first.lock.unlock();
        }
    }


    public double getBalance() {
        return balance.get();
    }

    public String getIdAccount() {
        return idAccount;
    }

}
