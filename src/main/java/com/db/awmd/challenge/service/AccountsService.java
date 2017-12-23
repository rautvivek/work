package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

	@Getter
	private final AccountsRepository accountsRepository;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	public AccountsRepository getAccountsRepository() {
		return accountsRepository;
	}

	public void transferMoney(String fromAccountId, String toAccountId, double amount)
			throws InsufficientBalanceException, InterruptedException {
		accountsRepository.transferMoney(fromAccountId, toAccountId, amount);
	}

	public void depositAmount(String accountId, double amount) throws InterruptedException {
		accountsRepository.depositMoney(accountId, amount);
	}

	public void withdrawAmount(String accountId, double amount)
			throws InsufficientBalanceException, InterruptedException {
		accountsRepository.withdrawMoney(accountId, amount);
	}
}
