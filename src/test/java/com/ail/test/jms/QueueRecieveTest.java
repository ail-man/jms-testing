package com.ail.test.jms;

import javax.jms.TextMessage;

import org.junit.Test;

public class QueueRecieveTest {

	private static final String URL = "t3://localhost:7004";
	private static final String JMS_FACTORY = "jms/WLReceiverQCF";
	private static final String QUEUE = "jms/WLReceiverQueue";

	@Test
	public void send() throws Exception {
		QueueManager qm = new QueueManager(URL, JMS_FACTORY, QUEUE);
		qm.init();

		qm.send("Test message 1");
		qm.send("Test message 2");
	}

	@Test
	public void recieve() throws Exception {
		QueueManager qm = new QueueManager(URL, JMS_FACTORY, QUEUE);
		qm.init();

		System.out.println(((TextMessage) qm.recieve()).getText());
		System.out.println(((TextMessage) qm.recieve()).getText());
	}

}
