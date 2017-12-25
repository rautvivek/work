package com.db.awmd.challenge.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferTransaction extends Transaction {
	@NotEmpty
	private String fromAccountId;
	@NotEmpty
	private String toAccountId;

	public TransferTransaction() {
		// todo
	}

	@JsonCreator
	public TransferTransaction(@JsonProperty("fromAccountId") String fromAcountId, @JsonProperty("toAccountId") String toAccountId, 
			@JsonProperty("amount") BigDecimal amount) {
		super();
		this.fromAccountId = fromAcountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
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
