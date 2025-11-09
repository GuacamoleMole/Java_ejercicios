package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int NUM_OPERATIONS = 10;
    private static final int NUM_OPTIONS = 3;
    private static final double DEPOSIT_AMOUNT = 100;
    private static final double WITHDRAW_AMOUNT = 250;
    private static final double TRANSFER_AMOUNT = 10;

    public static void main(String[] args) {
        System.out.println("=== SISTEMA BANCARIO CONCURRENTE ===\n");

        BankAccount[] accounts = {
                new BankAccount("ACC-001", 0),
                new BankAccount("ACC-002", 0),
                new BankAccount("ACC-004", 0),
                new BankAccount("ACC-005", 0)
        };
        System.out.println("Saldos iniciales:");
        printBalances(accounts);

        List<CompletableFuture<Void>> operations = new ArrayList<>();
        Random random = new Random();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 0; i < NUM_OPERATIONS; i++) {
                final int opNum = i + 1;

                CompletableFuture<Void> operation = CompletableFuture.runAsync(() -> {
                    try {
                        performRandomOperation(opNum, accounts, random);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        System.err.println("Error en operación: " + e.getMessage());
                    }
                }, executor);

                operations.add(operation);
            }

            System.out.println("\nEsperando que terminen todas las operaciones...\n");
            CompletableFuture.allOf(operations.toArray(new CompletableFuture[0])).join();

            System.out.println("\n=== OPERACIONES COMPLETADAS ===");
            System.out.println("Saldos finales:");
            printBalances(accounts);
        }
    }

    private static void performRandomOperation(int opNum, BankAccount[] accounts, Random random)
            throws InterruptedException {
        int operationType = random.nextInt(NUM_OPTIONS);
        BankAccount account1 = accounts[random.nextInt(accounts.length)];
        switch (operationType) {
            case 0: // Deposit
                performDeposit(opNum, account1, DEPOSIT_AMOUNT);
                break;
            case 1: // Withdraw
                performWithdraw(opNum, account1, WITHDRAW_AMOUNT);
                break;
            case 2: // Transfer
                BankAccount account2 = accounts[random.nextInt(accounts.length)];
                if (account1 != account2) {
                    performTransfer(opNum, account1, account2, TRANSFER_AMOUNT);
                }
                break;
            default:
                break;
        }
    }


    private static void performDeposit(int opNum, BankAccount account, double amount) {
        account.deposit(amount);
        System.out.printf("[Op %d] ✅ Depósito: %.2f en %s%n",
                opNum, amount, account.getIdAccount());
    }


    private static void performWithdraw(int opNum, BankAccount account, double amount) {
        boolean success = account.withdraw(amount);
        if (success) {
            System.out.printf("[Op %d] ✅ Retiro: %.2f de %s%n",
                    opNum, amount, account.getIdAccount());
        } else {
            System.out.printf("[Op %d] ❌ Retiro rechazado: %.2f de %s (saldo insuficiente)%n",
                    opNum, amount, account.getIdAccount());
        }
    }


    private static void performTransfer(int opNum, BankAccount source, BankAccount destination, double amount) {
        boolean transferred = source.transferTo(destination, amount);
        if (transferred) {
            System.out.printf("[Op %d] ✅ Transferencia: %.2f de %s a %s%n",
                    opNum, amount, source.getIdAccount(), destination.getIdAccount());
        } else {
            System.out.printf("[Op %d] ❌ Transferencia rechazada: %.2f de %s a %s (saldo insuficiente)%n",
                    opNum, amount, source.getIdAccount(), destination.getIdAccount());
        }
    }

    private static void printBalances(BankAccount[] accounts) {
        for (BankAccount account : accounts) {
            System.out.printf("%s: $%.2f%n", account.getIdAccount(), account.getBalance());
        }
    }

}