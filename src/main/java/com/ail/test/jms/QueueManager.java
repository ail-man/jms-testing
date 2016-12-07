/*
* https://blogs.oracle.com/soaproactive/entry/jms_step_2_using_the
* http://ibswings.blogspot.ru/2008/02/integrating-mq-broker-6-with-bea.html
* https://www.captechconsulting.com/blogs/oracle-weblogic-103-and-ibm-mq---can-they-play-nicely
* https://dzone.com/articles/solving-ibm-mq-client-error-%E2%80%93
* http://www-01.ibm.com/support/docview.wss?uid=swg21166937
* http://www.ibm.com/developerworks/websphere/techjournal/0502_woolf/0502_woolf.html#sec3-1
* http://www.ibm.com/support/knowledgecenter/SSFKSJ_7.5.0/com.ibm.mq.mig.doc/q001110_.htm
* http://www.ibm.com/support/knowledgecenter/SSFKSJ_7.5.0/com.ibm.mq.ref.adm.doc/q083460_.htm
* http://www.ibm.com/support/knowledgecenter/SSFKSJ_9.0.0/com.ibm.mq.ins.doc/q008640_.htm
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

	public void init(String userName, String password) throws NamingException, JMSException {
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, url);
		InitialContext ctx = new InitialContext(env);

		qconFactory = (QueueConnectionFactory) ctx.lookup(jmsFactory);
		qcon = qconFactory.createQueueConnection(userName, password);
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