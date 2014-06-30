package cn.com.chenp.jms;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;

import cn.com.chenp.util.JndiHelper;

public abstract class JmsClient {

	public static JmsClient newInstance(String facName, String destName) throws Exception
	{
		Object factory = JndiHelper.lookup(facName);
		Object dest = JndiHelper.lookup(destName);

		if (factory instanceof TopicConnectionFactory) {
			assertType(dest, Topic.class);
			return new JmsTopicClient((TopicConnectionFactory) factory, (Topic) dest);
		}
		else if (factory instanceof QueueConnectionFactory) {
			assertType(dest, Queue.class);
			return new JmsQueueClient((QueueConnectionFactory) factory, (Queue) dest);
		}
		else {
			throw new IllegalArgumentException(factory + " is not a JMS ConnectionFactory");
		}
	}

	private static void assertType(Object obj, Class cls)
	{
		if (! cls.isInstance(obj)) {
			throw new IllegalArgumentException(obj + " is not a JMS " + cls);
		}
	}

	protected abstract Connection getConnection();

	protected abstract Session getSession();

	protected abstract MessageProducer getProducer();

	protected abstract void sendMessage(Message m) throws JMSException;

	public void send(Object msg) throws JMSException
	{
		Message m;
		Session session = getSession();

		if (msg instanceof String) {
			m = session.createTextMessage((String) msg);
		}
		else if (msg instanceof Serializable) {
			m = session.createObjectMessage((Serializable) msg);
		}
		else {
			throw new IllegalArgumentException(String.valueOf(msg));
		}

		sendMessage(m);
	}

	public void close()
	{
		try {
			getProducer().close();
		}
		catch (Throwable e) {}

		try {
			getSession().close();
		}
		catch (Throwable e) {}

		try {
			getConnection().close();
		}
		catch (Throwable e) {}
	}

	public static void sendTo(String facName, String destName, Object msg) throws Exception
	{
		JmsClient c = JmsClient.newInstance(facName, destName);
		c.send(msg);
		c.close();
	}

}
