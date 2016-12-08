package com.ail.test.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "jms/WLRelayQCF"),
				@ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = "jms/WLRelayQueue")
		}, mappedName = "jms/WLRelayQueue"
)

public class TestMDB2 implements MessageListener {

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println("nnt(mdb) MyMDB Received n" + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}