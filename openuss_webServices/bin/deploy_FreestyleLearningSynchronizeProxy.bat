rem this assumes webserver is running on port 8080
@echo off 
java -cp ../lib/axis.jar;../lib/activation.jar;../lib/axis-ant.jar;../lib/commons-discovery-0.2.jar;../lib/commons-logging-1.0.4.jar;../lib/jaxrpc.jar;../lib/log4j-1.2.8.jar;../lib/mail.jar;../lib/saaj.jar;../lib/wsdl4j-1.5.1.jar;../lib/xercesImpl.jar org.apache.axis.client.AdminClient FreestyleLearningSynchronizeProxy.wsdd
