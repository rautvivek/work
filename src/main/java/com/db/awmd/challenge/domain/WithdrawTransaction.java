package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

public class WithdrawTransaction extends Transaction {
	private String accountId;

	public WithdrawTransaction() {
		// todo
	}

	public WithdrawTransaction(String accountId, BigDecimal amount, TransactionType withdrawn) {
		super(withdrawn, amount);
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
