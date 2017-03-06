package com.ail.test.jms;

import java.util.Hashtable;

import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class IBMMQSendTest {

	private static final String HOST_NAME = "mint";
	private static final String QUEUE_MANAGER_NAME = "MQTest";
	private static final String CHANNEL_NAME = "HPT5.CLNT.WL";
	private static final int PORT = 1414;
	private static final String USERID = "mquser";
	private static final String PASSWORD = "mquser";
	private static final String SSL_CIPHER_SUITE = "SSL_RSA_WITH_3DES_EDE_CBC_SHA";

	public static void main(String[] args) throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "D:/Temp/mint/APP1.jks");
		System.setProperty("javax.net.ssl.keyStore", "D:/Temp/mint/APP1.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "mint");
		//		System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");

		//		MQEnvironment.hostname = HOST_NAME;
		//		MQEnvironment.port = PORT;
		//		MQEnvironment.channel = CHANNEL_NAME;
		//		MQEnvironment.sslCipherSuite = SSL_CIPHER_SUITE;

		Hashtable<String, Object> mqht = new Hashtable<>();
		mqht.put(CMQC.CHANNEL_PROPERTY, CHANNEL_NAME);
		mqht.put(CMQC.HOST_NAME_PROPERTY, HOST_NAME);
		mqht.put(CMQC.PORT_PROPERTY, PORT);
		mqht.put(CMQC.SSL_CIPHER_SUITE_PROPERTY, SSL_CIPHER_SUITE);
		mqht.put(CMQC.USER_ID_PROPERTY, USERID);
		mqht.put(CMQC.PASSWORD_PROPERTY, PASSWORD);

		MQQueueManager qMgr = new MQQueueManager(QUEUE_MANAGER_NAME, mqht);

		//
		//		MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
		//		cf.setHostName(HOST_NAME);
		//		cf.setPort(PORT);
		//
		//		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		//
		//		cf.setQueueManager(QUEUE_MANAGER_NAME);
		//		cf.setChannel(CHANNEL_NAME);
		//
		//		cf.setSSLCipherSuite(SSL_CIPHER_SUITE);
		//		//		cf.setSSLFipsRequired(true);
		//
		//		//			MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
		//		MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection(USERID, PASSWORD);
		//
		//		MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		//		MQQueue queue = (MQQueue) session.createQueue("queue:///MQTestQueue");
		//
		//		MQQueueSender sender = (MQQueueSender) session.createSender(queue);
		//		MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);
		//
		//		long uniqueNumber = System.currentTimeMillis() % 1000;
		//
		//		TextMessage message = session.createTextMessage("Basic Queue Test " + uniqueNumber);
		//
		//		// Start the connection
		//		connection.start();
		//		System.out.println("Sent message to Queue MQSenderQueue: " + message.getText());
		//		sender.send(message);
		//		System.out.println("Message Sent OK.\n");
		//
		//		System.out.println("Recieve message from Queue MQTestQueue...");
		//		System.out.println("Message Recieved: " + ((TextMessage) receiver.receive(1000L)).getText());
		//
		//		sender.close();
		//		receiver.close();
		//
		//		session.close();
		//		connection.close();
	}

}