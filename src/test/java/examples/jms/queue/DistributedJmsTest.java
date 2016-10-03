package examples.jms.queue;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.sun.messaging.ConnectionConfiguration;
import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Queue;
import org.junit.Ignore;
import org.junit.Test;

public class DistributedJmsTest {

	@Test
	@Ignore
	public void test() throws Exception {
		ConnectionFactory connFactory = new ConnectionFactory();
		connFactory.setProperty(ConnectionConfiguration.imqAddressList, "localhost:7003");

		Queue myQueue = new Queue("DistributedQueue-0");
		//		Queue myQueue = new Queue("SystemModule-0!JMSServer-0@new_ManagedServer_1@DistributedQueue-0");

		try (Connection connection = connFactory.createConnection();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer producer = session.createProducer(myQueue))
		{

			Message message = session.createTextMessage("this is my test message");
			producer.send(message);
		}
	}

}
