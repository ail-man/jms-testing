package com.ail.test.jms;

import java.io.IOException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import com.ail.test.common.FileReader;

public class TestJms {
	private FileReader fileReader;
	private QueueManager queueManager;

	public TestJms(String url, String jmsFactory, String jmsQueue) {
		fileReader = new FileReader();
		queueManager = new QueueManager(url, jmsFactory, jmsQueue);
	}

	public void init(String login, String pass) throws JMSException, NamingException {
		if (login == null) {
			queueManager.init();
		} else {
			queueManager.init(login, pass);
		}
	}

	public void send(String fileName) throws IOException, JMSException {
		queueManager.send(fileReader.readFile(fileName));
	}

}
