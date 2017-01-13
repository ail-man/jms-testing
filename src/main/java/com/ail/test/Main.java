package com.ail.test;

import com.ail.test.jms.TestJms;

public class Main {

	public static void main(final String[] args) throws Exception {
		final String url = args[0];
		final String jmsFactory = args[1];
		final String jmsQueue = args[2];
		final String login = args[3];
		final String pass = args[4];
		final String fileName = args[5];

		TestJms testJms = new TestJms(url, jmsFactory, jmsQueue);
		testJms.init(login, pass);
		testJms.send(fileName);
	}

}
