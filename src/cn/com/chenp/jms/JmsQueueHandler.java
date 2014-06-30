package cn.com.chenp.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

public class JmsQueueHandler extends JmsHandler {
	private QueueConnection cn;
	private QueueSession session;
	private QueueReceiver consumer;
	JmsQueueHandler(QueueConnectionFactory factory, Queue dest) throws Exception
	{
		cn = factory.createQueueConnection();

		try {
			boolean transacted = false;
			int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
			session = cn.createQueueSession(transacted, acknowledgeMode);
			consumer = session.createReceiver(dest);
		}
		catch (JMSException e) {
			close();
			throw e;
		}
	}
	@Override
	protected Connection getConnection() {
		// TODO Auto-generated method stub
		return cn;
	}

	@Override
	protected Session getSession() {
		// TODO Auto-generated method stub
		return session;
	}

	@Override
	protected MessageConsumer getConsumer() {
		// TODO Auto-generated method stub
		return consumer;
	}

}
