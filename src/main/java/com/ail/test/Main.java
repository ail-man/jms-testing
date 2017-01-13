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

	/*
	 * Для тестирования необходимо:
	 1) настроить JMS-очередь на WebLogic, где устанавливается сборка (по аналогии с Audit queue).
	 2) сконфигурировать эту очередь в SVDL -> Целевые системы (тип SBRF-MQ)
	 3) создать задачу и в качестве источника данных указать TargetSystem (не File!!!), и выбрать сконфигурированный TargetSystem.
	 4) сделать задачу активной (обязательно!), т.к. запуск задачи вручную для TargetSystem работать не будет (по этой проблеме есть отдельная заявка - SBRF-1863).
	 5) закинуть сообщение в кодировке UTF-8 (поддерживается с BOM и без BOM) в очередь и смотреть логи.
	 */

}
