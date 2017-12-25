package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferTransaction;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientBalanceException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);
  void clearAccounts();
  void transferMoney(TransferTransaction transferAmount) throws InsufficientBalanceException,InterruptedException;
  void depositMoney(String accountId, Double money) throws InterruptedException;
  void withdrawMoney(String accountId,Double money) throws InsufficientBalanceException,InterruptedException;
}
