package com.ail.test.jms;

import javax.jms.TextMessage;

import com.ail.test.common.FileReader;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class QueueRecieveTest {

	private static final String URL = "t3://localhost:7005";
	private static final String RECIEVER_JMS_FACTORY = "jms/SVDLTestCF";
	private static final String RECIEVER_QUEUE = "jms/SVDLTestQueue";

	@Test
	public void send() throws Exception {
		QueueManager qm = new QueueManager(URL, RECIEVER_JMS_FACTORY, RECIEVER_QUEUE);
		qm.init();

		qm.send(new FileReader().readFile("D:\\Temp\\_svdl\\out\\SendRegisterTerminalDataRq.utf8_noBOM"));
	}

	@Test
	public void recieve() throws Exception {
		QueueManager qm = new QueueManager(URL, RECIEVER_JMS_FACTORY, RECIEVER_QUEUE);
		qm.init();

		System.out.println(((TextMessage) qm.recieve()).getText());
	}

	@Test
	public void testReadFile() throws Exception {
		String message = new FileReader().readFile("D:\\Temp\\_svdl\\out\\SendRegisterTerminalDataRq.utf8");
		System.out.println(message);
	}

	@Test
	public void testReadFile2() throws Exception {
		byte[] source = readFile2("D:\\Temp\\_svdl\\out\\SendRegisterTerminalDataRq.utf8");
		System.out.println(new String(source));
	}

	private byte[] readFile2(String fileName) throws Exception {
		return IOUtils.toByteArray(new java.io.FileReader(fileName));
	}
}
