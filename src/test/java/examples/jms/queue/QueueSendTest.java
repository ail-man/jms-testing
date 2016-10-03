package examples.jms.queue;

import org.junit.Test;

public class QueueSendTest {

	@Test
	public void main() throws Exception {
		QueueSend.main(new String[] { "t3://localhost:7003/" });
	}

}