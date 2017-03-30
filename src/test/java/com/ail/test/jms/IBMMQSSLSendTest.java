package com.ail.test.jms;

import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;
import com.ibm.msg.client.wmq.WMQConstants;

public class IBMMQSSLSendTest {

	private static final String HOST_NAME = "mint";
	private static final String QUEUE_MANAGER_NAME = "MQTest";
	private static final String CHANNEL_NAME = "HPT5.CLNT.WL";
	private static final int PORT = 1414;
	private static final String USERID = "mquser";
	private static final String PASSWORD = "mquser";
	private static final String SSL_CIPHER_SUITE = "TLS_RSA_WITH_AES_128_CBC_SHA256";
	private static final String QUEUE_NAME = "MQTestQueue";

	public static void main(String[] args) throws Exception {
		//		System.setProperty("javax.net.ssl.keyStore", "D:/Temp/mint/APP1.jks");
		//		System.setProperty("javax.net.ssl.keyStorePassword", "mint");
		System.setProperty("javax.net.ssl.trustStore", "D:/Temp/mint/APP1.jks");
		//		System.setProperty("javax.net.ssl.trustStorePassword", "mint");

		System.setProperty("javax.net.debug", "all");
		System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
		//		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		// 1st option
		//		MQEnvironment.hostname = HOST_NAME;
		//		MQEnvironment.port = PORT;
		//		MQEnvironment.channel = CHANNEL_NAME;
		//		MQEnvironment.sslCipherSuite = SSL_CIPHER_SUITE;

		// 2nd option
		//		Hashtable<String, Object> mqht = new Hashtable<>();
		//		mqht.put(CMQC.CHANNEL_PROPERTY, CHANNEL_NAME);
		//		mqht.put(CMQC.HOST_NAME_PROPERTY, HOST_NAME);
		//		mqht.put(CMQC.PORT_PROPERTY, PORT);
		//		mqht.put(CMQC.SSL_CIPHER_SUITE_PROPERTY, SSL_CIPHER_SUITE);
		//		mqht.put(CMQC.USER_ID_PROPERTY, USERID);
		//		mqht.put(CMQC.PASSWORD_PROPERTY, PASSWORD);
		//		MQQueueManager qMgr = new MQQueueManager(QUEUE_MANAGER_NAME, mqht);

		// 3rd option
		MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
		cf.setHostName(HOST_NAME);
		cf.setPort(PORT);

		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);

		cf.setQueueManager(QUEUE_MANAGER_NAME);
		cf.setChannel(CHANNEL_NAME);

		cf.setSSLCipherSuite(SSL_CIPHER_SUITE);
		//		cf.setSSLFipsRequired(true); // not needed

		//			MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
		MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection(USERID, PASSWORD);

		MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		MQQueue queue = (MQQueue) session.createQueue("queue:///" + QUEUE_NAME);

		MQQueueSender sender = (MQQueueSender) session.createSender(queue);
		MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);

		long uniqueNumber = System.currentTimeMillis() % 1000;

		TextMessage message = session.createTextMessage("Basic Queue Test " + uniqueNumber);

		// Start the connection
		connection.start();
		System.out.println("Sent message to Queue MQSenderQueue: " + message.getText());
		sender.send(message);
		System.out.println("Message Sent OK.\n");

		System.out.println("Recieve message from Queue " + QUEUE_NAME + "...");
		System.out.println("Message Recieved: " + ((TextMessage) receiver.receive(1000L)).getText());

		sender.close();
		receiver.close();

		session.close();
		connection.close();
	}

}