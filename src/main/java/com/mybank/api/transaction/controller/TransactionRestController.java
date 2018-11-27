package com.mybank.api.transaction.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class TransactionRestController {

	Logger logger = LoggerFactory.getLogger(TransactionRestController.class);

	@Autowired
	private ApplicationContext _applicationContext;

	// -------------------Retrieve All Transactions--------------------------------------------------------

	@ApiOperation(value = "Get All Transactions")
	@RequestMapping(value = "/transactions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllTransactions(
			@RequestParam(value = "transactionType", required = false) String transactionType) {

		logger.info("Serviving All Transactions...");
		String camelRouteID = null;

		CamelContext camelctx = (CamelContext) _applicationContext.getBean("get-all-transactions");
		if (transactionType != null) {
			logger.info("TransactionType passed is --" + transactionType);
			camelRouteID = "direct://gettransactionlistByType";
			
		} else {
			camelRouteID = "direct://gettransactionlist";
		}
		Message result = null;
		try {
			FluentProducerTemplate fluent = camelctx.createFluentProducerTemplate();
			fluent.setDefaultEndpointUri(camelRouteID);

			logger.info("Calling REST...");
			result = fluent.request(Message.class);
		} catch (CamelExecutionException ceEx) {
			logger.error("Error invoking openBanking API--", ceEx);
		}
		String resultStr = result.getBody(String.class);
		logger.info("Response is --" + resultStr);
		return ResponseEntity.ok(resultStr);
	}

	// -------------------Retrieve Total Amount by Transaction type--------------------------------------------------------
	
	@ApiOperation(value = "Get Total Transaction Amount")
	@RequestMapping(value = "/getTransactionTotalAmount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllTransactionsTotalAmount(
			@RequestParam(value = "transactionType", required = true) String transactionType) {

		logger.info("Calculating Total Amount for all Transactions...");

		CamelContext camelctx = (CamelContext) _applicationContext.getBean("get-all-transactions");
		Message result = null;
		try {
			FluentProducerTemplate fluent = camelctx.createFluentProducerTemplate();
			fluent.setDefaultEndpointUri("direct:gettransactionTotalAmount");

			logger.info("Calling REST...");
			result = fluent.request(Message.class);
		} catch (CamelExecutionException ceEx) {
			logger.error("Error invoking openBanking API--", ceEx);
		}
		String resultStr = result.getBody(String.class);
		logger.info("Response is --" + resultStr);
		return ResponseEntity.ok(resultStr);
	}

}
