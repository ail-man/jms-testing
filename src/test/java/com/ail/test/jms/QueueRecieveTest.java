package com.ail.test.jms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.jms.TextMessage;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class QueueRecieveTest {

	private static final String URL = "t3://localhost:7005";
	private static final String RECIEVER_JMS_FACTORY = "jms/WLRelayQCF";
	private static final String RECIEVER_QUEUE = "jms/WLRelayQueue";

	@Test
	public void send() throws Exception {
		QueueManager qm = new QueueManager(URL, RECIEVER_JMS_FACTORY, RECIEVER_QUEUE);
		qm.init("mquser", "mquser");

		qm.send(readFile("D:\\Temp\\_svdl\\out\\SendRegisterTerminalDataRq.utf8_noBOM"));
	}

	@Test
	public void recieve() throws Exception {
		QueueManager qm = new QueueManager(URL, RECIEVER_JMS_FACTORY, RECIEVER_QUEUE);
		qm.init("mquser", "mquser");

		System.out.println(((TextMessage) qm.recieve()).getText());
	}

	@Test
	public void testReadFile() throws Exception {
		String message = readFile("D:\\Temp\\_svdl\\out\\SendRegisterTerminalDataRq.utf8");
		System.out.println(message);
	}

	@Test
	public void testReadFile2() throws Exception {
		byte[] source = readFile2("D:\\Temp\\_svdl\\out\\SendRegisterTerminalDataRq.utf8");
		System.out.println(new String(source));
	}

	private String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	byte[] readFile2(String fileName) throws Exception {
		return IOUtils.toByteArray(new FileReader(fileName));
	}
}
