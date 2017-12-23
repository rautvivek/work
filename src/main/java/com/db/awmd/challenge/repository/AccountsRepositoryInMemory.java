package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.DepositTransaction;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.domain.TransactionType;
import com.db.awmd.challenge.domain.TransferTransaction;
import com.db.awmd.challenge.domain.WithdrawTransaction;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.service.EmailNotificationService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();
	private final BlockingQueue<Transaction> transferQ = new LinkedBlockingQueue<>();
	@Autowired
	EmailNotificationService emailNotificationService;

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	@Override
	/* method is support money transfer */
	public void transferMoney(String fromAccountId, String toAccountId, Double amount)
			throws InsufficientBalanceException, InterruptedException {
		// TODO Auto-generated method stub
		Transaction transferTransaction = new TransferTransaction(fromAccountId, toAccountId,
				BigDecimal.valueOf(amount), TransactionType.TRANSFER);
		transferQ.put(transferTransaction);
		beginTransaction();

	}

	@Override
	/* method is support money deposit */
	public void depositMoney(String accountId, Double money) throws InterruptedException {
		// TODO Auto-generated method stub
		Transaction depositTransaction = new DepositTransaction(accountId, BigDecimal.valueOf(money),
				TransactionType.DEPOSIT);
		transferQ.put(depositTransaction);
		beginTransaction();

	}

	@Override
	/* method is support money withdrawal */
	public void withdrawMoney(String accountId, Double money)
			throws InsufficientBalanceException, InterruptedException {
		// TODO Auto-generated method stub
		Transaction withdrawTransaction = new WithdrawTransaction(accountId, BigDecimal.valueOf(money),
				TransactionType.WITHDRAWN);
		transferQ.put(withdrawTransaction);
		beginTransaction();

	}

	/* method is used to initiate transaction */
	private void beginTransaction() {
		try {
			Transaction transaction = transferQ.take();
			if (transaction.getTransactionType().equals(TransactionType.TRANSFER)) {
				beginFundTransferTransaction((TransferTransaction) transaction);
			} else if (transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
				beginDepositTransaction((DepositTransaction) transaction);
			} else if (transaction.getTransactionType().equals(TransactionType.WITHDRAWN)) {
				beginWithdrawTransaction((WithdrawTransaction) transaction);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* method is used to initiate fund transfer transaction */
	private void beginFundTransferTransaction(TransferTransaction transaction) throws InsufficientBalanceException {
		Account fromAccount = getAccount(transaction.getFromAccountId());
		if (fromAccount != null) {
			synchronized (fromAccount) {
				if (IsAmountWithdrawnableFromAccout(fromAccount.getAccountId(), transaction.getAmount())) {
					withdrawAmount(fromAccount, transaction.getAmount());
					Account toAccount = getAccount(transaction.getToAccountId());
					depositAmount(toAccount, transaction.getAmount());

				} else {
					throw new InsufficientBalanceException("Insufficient balance, can not withraw required amount");
				}
			}
		}

	}

	/* method is used to initiate deposit transaction */
	private void beginDepositTransaction(DepositTransaction transaction) {
		Account account = getAccount(transaction.getAccountId());
		if (account != null) {
			synchronized (account) {
				depositAmount(account, transaction.getAmount());
			}
		}
	}

	/* method is used to initiate withdrawn transaction */
	private void beginWithdrawTransaction(WithdrawTransaction transaction) {
		Account account = getAccount(transaction.getAccountId());
		if (account != null) {
			synchronized (account) {
				if (IsAmountWithdrawnableFromAccout(account.getAccountId(), transaction.getAmount())) {
					withdrawAmount(account, transaction.getAmount());
				} else {
					throw new InsufficientBalanceException("Insufficient balance, can not withraw required amount");
				}

			}
		}
	}

	/* method is used to check is required withdrawn possible from given account */
	public boolean IsAmountWithdrawnableFromAccout(String accountId, BigDecimal amount) {
		Account account = getAccount(accountId);
		BigDecimal balance = account.getBalance().subtract(amount);
		if (balance.intValue() > 0) {
			return true;
		}
		return false;
	}

	/* method is used for amount deposit and send notification */
	public void depositAmount(Account account, BigDecimal amount) {
		account.deposit(amount);
		emailNotificationService.notifyAboutTransfer(account,
				"amount " + amount + " is deposited. Avaiable balance: " + account.getBalance());
	}

	/* method is used for amount withdraw and send notification */
	public void withdrawAmount(Account account, BigDecimal amount) {
		account.withdraw(amount);
		emailNotificationService.notifyAboutTransfer(account,
				"amount " + amount + " is withdrawn. Avaiable balance: " + account.getBalance());
	}

}
