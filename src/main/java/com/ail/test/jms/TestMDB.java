package com.ail.test.jms;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Class: TestMDB
 *
 * @author Ishwara Varnasi
 * @date Feb 17, 2008
 */
@MessageDriven(name = "TestMDB", mappedName = "jms/WLRelayQueue",
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType",
				propertyValue = "javax.jms.Queue") }
)
public class TestMDB implements MessageListener {

	@Resource(name = "WLRelayQueue", mappedName = "jms/WLRelayQueue")
	private javax.jms.Queue queue;
	@Resource(name = "WLRelayQCF", mappedName = "jms/WLRelayQCF")
	private javax.jms.QueueConnectionFactory tcf;

	public void onMessage(Message msg) {
		System.out.println("\n=========================================");
		System.out.println("=> Message received!");

		if (msg instanceof TextMessage) {

			try {
				System.out.println("=> Message: "
						+ ((TextMessage) msg).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		// Place the message on to the reply queue (WLReplyQueue)
		putMessage(msg);
		System.out.println("=========================================");
	}

	public void putMessage(javax.jms.Message msg) {
		System.out.println("=> Placing message on WLReplyQueue...");
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		QueueSender queueSender = null;

		try {
			queueConnection = tcf.createQueueConnection();
			queueSession = queueConnection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			queueSender = queueSession.createSender(queue);
			queueSender.send(msg);
			System.out.println("=> Message sent!");

		} catch (Exception e) {
			System.out.println("Exception: ");
			e.printStackTrace();

			if (e instanceof JMSException) {
				((JMSException) e).getLinkedException().printStackTrace();
			}

		} finally {

			try {
				System.out.println("=> Closing Connection");
				queueSession.close();
				queueConnection.close();
			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();

				if (e instanceof JMSException) {
					((JMSException) e).getLinkedException().printStackTrace();
				}
			}
		}
	}

	@PostConstruct
	public void postCreate() {
		System.out.println("Bean Created");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("Bean Destroyed");
	}
}