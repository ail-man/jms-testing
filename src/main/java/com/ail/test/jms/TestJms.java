package com.ail.test.jms;

import java.io.IOException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import com.ail.test.common.FileReader;

public class TestJms {

	public static void main(final String[] args) {
		final String URL = args[0];
		final String RECIEVER_JMS_FACTORY = args[1];
		final String RECIEVER_QUEUE = args[2];
		final String FILE_NAME = args[3];

		QueueManager qm = new QueueManager(URL, RECIEVER_JMS_FACTORY, RECIEVER_QUEUE);
		FileReader fileReader = new FileReader();

		try {
			qm.init();
			qm.send(fileReader.readFile(FILE_NAME));
		} catch (NamingException | JMSException | IOException e) {
			e.printStackTrace();
		}
	}

}
