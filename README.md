# mybank
POC usingJava Spring and Apache Camel



The 3 APIs supported are --
=============================

1. Get All Transactions - http://localhost:8888/mybank/api/transactions
2. Get Transaction by Type - http://localhost:8888/mybank/api/transactions?transactionType=sandbox-payment
(Does not work fully! - transaction type not dynamic)
3. Get Total Amount - http://localhost:8888/mybank/api/getTransactionTotalAmount


Note:
1. Need to be on Java 1.8
2. Need to add openbankproject certificate to your java to make this work

keytool -import -trustcacerts -keystore cacerts -storepass changeit -noprompt -alias openbankcert -file /Users/<userid>/Documents/apisandboxopenbankprojectcom.crt 

