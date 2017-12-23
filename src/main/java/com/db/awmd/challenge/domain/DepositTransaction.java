package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

public class DepositTransaction extends Transaction {

	private String accountId;

	public DepositTransaction() {
		// todo
	}

	public DepositTransaction(String accountId, BigDecimal amount, TransactionType deposit) {
		super(deposit, amount);
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
