package com.lypaka.lypakautils.MiscHandlers;

import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class LogicalPixelmonMoneyHandler {

    public static void add (UUID uuid, double amount) throws ExecutionException, InterruptedException {

        BankAccountProxy.getBankAccount(uuid).get().add(amount);
        BankAccountProxy.getBankAccount(uuid).get().updatePlayer();

    }

    public static void remove (UUID uuid, double amount) throws ExecutionException, InterruptedException {

        BankAccountProxy.getBankAccount(uuid).get().take(amount);
        BankAccountProxy.getBankAccount(uuid).get().updatePlayer();

    }

    public static double getBalance (UUID uuid) throws ExecutionException, InterruptedException {

        return BankAccountProxy.getBankAccount(uuid).get().getBalance().doubleValue();

    }

}
