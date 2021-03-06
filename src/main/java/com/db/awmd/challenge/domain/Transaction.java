package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class Transaction {

	protected TransactionType transactionType;
	@NotNull
	protected BigDecimal amount;

	public Transaction() {
		// todo
	}

	public Transaction(TransactionType transactionType, BigDecimal amount) {
		super();
		this.transactionType = transactionType;
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
