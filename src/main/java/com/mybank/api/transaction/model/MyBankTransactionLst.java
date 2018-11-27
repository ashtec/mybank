package com.mybank.api.transaction.model;

import java.util.ArrayList;
import java.util.List;

public class MyBankTransactionLst {

	private List<MyBankTransaction> transactions = new ArrayList<MyBankTransaction>();

	public List<MyBankTransaction> getTransactions() {
		return transactions;
	}

	public void addTransaction(MyBankTransaction transaction) {
		this.transactions.add(transaction);
	}

	@Override
	public String toString() {
		return "MyBankTransactionLst [transactions=" + transactions + "]";
	}
	
}
