package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

public class TransferTransaction extends Transaction {
	private String fromAccountId;
	private String toAccountId;

	public TransferTransaction() {
		// todo
	}

	public TransferTransaction(String fromAccountId, String toAccountId, BigDecimal amount,
			TransactionType transactionType) {
		super(transactionType, amount);
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;

	}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

}
