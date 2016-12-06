/*
* https://blogs.oracle.com/soaproactive/entry/jms_step_2_using_the
* */
package com.ail.test.jms;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueManager {

	private final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";

	private final String jmsFactory;
	private final String jmsQueue;
	private final String url;

	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender qsender;
	private QueueReceiver qReciever;
	private Queue queue;
	private TextMessage msg;

	public QueueManager(String url, String jmsFactory, String jmsQueue) {
		this.url = url;
		this.jmsFactory = jmsFactory;
		this.jmsQueue = jmsQueue;
	}

	public void init() throws NamingException, JMSException {
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, url);
		InitialContext ctx = new InitialContext(env);

		qconFactory = (QueueConnectionFactory) ctx.lookup(jmsFactory);
		qcon = qconFactory.createQueueConnection();
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) ctx.lookup(jmsQueue);
		qsender = qsession.createSender(queue);
		qReciever = qsession.createReceiver(queue);
		msg = qsession.createTextMessage();
		qcon.start();
	}

	public void send(String message) throws JMSException {
		msg.setText(message);
		qsender.send(msg);
	}

	public Message recieve() throws JMSException {
		return qReciever.receive(1000L);
	}

	public void close() throws JMSException {
		qsender.close();
		qsession.close();
		qcon.close();
	}
}