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
import org.junit.Test;

public class IBMMQSendTest {

	@Test
	public void testSendAndRecieve() throws Exception {
		MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
		cf.setHostName("mint");
		cf.setPort(1414);

		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);

		cf.setQueueManager("MQTest");
		cf.setChannel("HPT5.CLNT.WL");

		//			MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
		MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection("mquser", "mquser");

		MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		MQQueue queue = (MQQueue) session.createQueue("queue:///MQTestQueue");

		MQQueueSender sender = (MQQueueSender) session.createSender(queue);
		MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);

		long uniqueNumber = System.currentTimeMillis() % 1000;

		TextMessage message = session.createTextMessage("Basic Queue Test " + uniqueNumber);

		// Start the connection
		connection.start();
		System.out.println("Sent message to Queue MQSenderQueue: " + message.getText());
		sender.send(message);
		System.out.println("Message Sent OK.\n");

		System.out.println("Recieve message from Queue MQTestQueue: " + message.getText());
		System.out.println(((TextMessage) receiver.receive(1000L)).getText());
		System.out.println("Message Recieved OK.\n");

		sender.close();
		receiver.close();

		session.close();
		connection.close();
	}

}