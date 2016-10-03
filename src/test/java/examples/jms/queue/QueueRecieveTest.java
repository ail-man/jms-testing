package examples.jms.queue;

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

import org.junit.Test;

public class QueueRecieveTest {

	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	public final static String JMS_FACTORY = "jms/TestConnectionFactory";
	public final static String QUEUE = "jms/TestDistributedQueue";

	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender qsender;
	private QueueReceiver qReciever;
	private Queue queue;
	private TextMessage msg;

	@Test
	public void recieve() throws Exception {
		InitialContext ic = getInitialContext("t3://localhost:7004/");
		init(ic, QUEUE);
		Message message = qReciever.receive();
		System.out.println(message);
	}

	private InitialContext getInitialContext(String url) throws NamingException {
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, url);
		return new InitialContext(env);
	}

	public void init(Context ctx, String queueName) throws NamingException, JMSException {
		qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
		qcon = qconFactory.createQueueConnection();
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) ctx.lookup(queueName);
		qsender = qsession.createSender(queue);
		qReciever = qsession.createReceiver(queue);
		msg = qsession.createTextMessage();
		qcon.start();
	}

	public void close() throws JMSException {
		qsender.close();
		qsession.close();
		qcon.close();
	}
}
