package com.mybank.api.transaction.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.jayway.jsonpath.JsonPath;

public class TransactionAmountProcessor implements Processor {


	public void process(Exchange exchange) throws Exception {
		
        final Message inMessage = exchange.getIn();
        final String body = inMessage.getBody(String.class);
        
        Integer NumOftransactions = JsonPath.read(body, "$.transactions.length()");
        
        double totalRunningAmt = 0d;
        
        String currentRunningAmtStr = null;
   
        for(int i=0; i < NumOftransactions; i++) {

        	currentRunningAmtStr = JsonPath.read(body, "$.transactions[" + i + "].details.value.amount");
        	if(currentRunningAmtStr != null) {
        		totalRunningAmt += Double.parseDouble(currentRunningAmtStr);
        	}
 
        }
        
        inMessage.setBody(String.valueOf(totalRunningAmt));
      }


}
