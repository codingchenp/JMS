package cn.com.chenp.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

public class JmsTopicHandler extends JmsHandler {
	private TopicConnection cn;
	private TopicSession session;
	private TopicSubscriber consumer;
	
	JmsTopicHandler(TopicConnectionFactory factory, Topic dest) throws Exception
	{
		cn = factory.createTopicConnection();

		try {
			boolean transacted = false;
			int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
			session = cn.createTopicSession(transacted, acknowledgeMode);
			consumer = session.createSubscriber(dest);
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
