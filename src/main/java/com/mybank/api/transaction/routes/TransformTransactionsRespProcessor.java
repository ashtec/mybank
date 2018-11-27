package com.mybank.api.transaction.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mybank.api.transaction.model.MyBankTransaction;
import com.mybank.api.transaction.model.MyBankTransactionLst;

public class TransformTransactionsRespProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		final Message inMessage = exchange.getIn();
		final String body = inMessage.getBody(String.class);

		// Configuration configuration =
		// Configuration.builder().options(Option.DEFAULT_PATH_LEAF_TO_NULL).build();
		// com.jayway.jsonpath.DocumentContext domContext =
		// JsonPath.using(configuration).parse(body);

		Integer NumOftransactions = JsonPath.read(body, "$.transactions.length()");

		MyBankTransaction myBankTransaction = null;
		MyBankTransactionLst myBankTransactionLst = new MyBankTransactionLst();
		for (int i = 0; i < NumOftransactions; i++) {
			myBankTransaction = new MyBankTransaction();

			myBankTransaction.setId(JsonPath.read(body, "$.transactions[" + i + "].id"));
			myBankTransaction.setAccountId(JsonPath.read(body, "$.transactions[" + i + "].this_account.id"));
			myBankTransaction.setCounterpartyAccount(
					JsonPath.read(body, "$.transactions[" + i + "].other_account.number"));
			myBankTransaction.setCounterpartyName(
					JsonPath.read(body, "$.transactions[" + i + "].other_account.holder.name"));
			myBankTransaction.setCounterPartyLogoPath(
					JsonPath.read(body, "$.transactions[" + i + "].other_account.metadata.image_URL"));
			myBankTransaction.setInstructedAmount(
					JsonPath.read(body, "$.transactions[" + i + "].details.value.amount"));
			myBankTransaction.setInstructedCurrency(
					JsonPath.read(body, "$.transactions[" + i + "].details.value.currency"));
			myBankTransaction.setTransactionAmount(
					JsonPath.read(body, "$.transactions[" + i + "].details.value.amount"));
			myBankTransaction.setTransactionCurrency(
					JsonPath.read(body, "$.transactions[" + i + "].details.value.currency"));
			myBankTransaction
					.setTransactionType(JsonPath.read(body, "$.transactions[" + i + "].details.type"));
			myBankTransaction
					.setDescription(JsonPath.read(body, "$.transactions[" + i + "].details.description"));

			/*
			 * myBankTransaction.setCounterpartyAccount(JsonPath.read(body,
			 * "$.transactions[" + i + "].other_account.number").toString());
			 * myBankTransaction.setCounterpartyName(JsonPath.read(body, "$.transactions[" +
			 * i + "].other_account.holder.name").toString());
			 * myBankTransaction.setCounterPartyLogoPath(JsonPath.read(body,
			 * "$.transactions[" + i + "].other_account.metadata.image_URL").toString());
			 * myBankTransaction.setInstructedAmount(JsonPath.read(body, "$.transactions[" +
			 * i + "].details.value.amount").toString());
			 * myBankTransaction.setInstructedCurrency(JsonPath.read(body, "$.transactions["
			 * + i + "].details.value.currency").toString());
			 * myBankTransaction.setTransactionAmount(JsonPath.read(body, "$.transactions["
			 * + i + "].details.value.amount").toString());
			 * myBankTransaction.setTransactionCurrency(JsonPath.read(body,
			 * "$.transactions[" + i + "].details.value.currency").toString());
			 * myBankTransaction.setTransactionType(JsonPath.read(body, "$.transactions[" +
			 * i + "].details.type").toString());
			 * myBankTransaction.setDescription(JsonPath.read(body, "$.transactions[" + i +
			 * "].details.description").toString());
			 */

			myBankTransactionLst.addTransaction(myBankTransaction);

		}

		ObjectMapper mapper = new ObjectMapper();

		String resultJsonStr = mapper.writeValueAsString(myBankTransactionLst);

		inMessage.setBody(resultJsonStr);
	}

}
