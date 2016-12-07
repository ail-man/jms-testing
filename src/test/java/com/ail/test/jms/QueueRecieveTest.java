package com.ail.test.jms;

import javax.jms.TextMessage;

import org.junit.Test;

public class QueueRecieveTest {

	private static final String URL = "t3://localhost:7004";
	private static final String RECIEVER_JMS_FACTORY = "jms/WLReceiverQCF";
	private static final String RECIEVER_QUEUE = "jms/WLReceiverQueue";

	private static final String REPLY_JMS_FACTORY = "jms/WLReplyQCF";
	private static final String REPLY_QUEUE = "jms/WLReplyQueue";

	@Test
	public void send() throws Exception {
		QueueManager qm = new QueueManager(URL, REPLY_JMS_FACTORY, REPLY_QUEUE);
		qm.init("mquser", "mquser");

		qm.send("Test message " + System.currentTimeMillis());
	}

	@Test
	public void recieve() throws Exception {
		QueueManager qm = new QueueManager(URL, RECIEVER_JMS_FACTORY, RECIEVER_QUEUE);
		qm.init("mquser", "mquser");

		System.out.println(((TextMessage) qm.recieve()).getText());
	}

}
