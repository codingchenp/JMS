package cn.com.chenp.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;

import cn.com.chenp.util.JndiHelper;


/**
 * 通常要扩展 JmsListener 来使用.
 * class MyListener extends JmsListener ...
 * 重写 process(...) 系列的方法来响应消息,
 * 重写 handleError(...) 来处理错误, 参见 JmsListener.
 */
public abstract class JmsHandler {
	
	public static JmsHandler newInstance(String facName, String destName, JmsListener listener) throws Exception{
		Object factory = JndiHelper.lookup(facName);
		Object dest = JndiHelper.lookup(destName);
		JmsHandler h;
		if (factory instanceof TopicConnectionFactory) {
			assertType(dest, Topic.class);
			h = new JmsTopicHandler((TopicConnectionFactory) factory, (Topic) dest);
		}
		else if (factory instanceof QueueConnectionFactory) {
			assertType(dest, Queue.class);
			h = new JmsQueueHandler((QueueConnectionFactory) factory, (Queue) dest);
		}
		else {
			throw new IllegalArgumentException(factory + " is not a JMS ConnectionFactory");
		}

		Connection cn = h.getConnection();
		MessageConsumer mc = h.getConsumer();

		try {
			cn.setExceptionListener(listener);
		}
		catch (Throwable e) {}

		try {
			mc.setMessageListener(listener);
			cn.start();
		}
		catch (javax.jms.IllegalStateException ise) {
			cn.start();
			new Thread(new MsgRcv(mc, listener)).start();
		}

		return h;
	}
	
	private static class MsgRcv implements Runnable
	{
		private MessageConsumer consumer;
		private JmsListener listener;

		private MsgRcv(MessageConsumer mc, JmsListener listener)
		{
			this.consumer = mc;
			this.listener = listener;
		}

		public void run()
		{
			Message m;

			try {
				while ((m = consumer.receive()) != null) {
					listener.onMessage(m);
				}
			}
			catch (JMSException e) {
				listener.onException(e);
			}
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

	protected abstract MessageConsumer getConsumer();

	public void close()
	{
		try {
			getConsumer().close();
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
}
