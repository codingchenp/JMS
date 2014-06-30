package cn.com.chenp.jms;

import javax.jms.*;
//╤сап
public class JmsQueueClient extends JmsClient {
	private QueueConnection cn;
	private QueueSession session;
	private QueueSender producer;
	
	JmsQueueClient(QueueConnectionFactory factory,Queue dest) throws Exception{
		cn = factory.createQueueConnection();
		try {
			boolean transacted = false;
			int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
			session = cn.createQueueSession(transacted, acknowledgeMode);
			producer = session.createSender(dest);
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
	protected MessageProducer getProducer() {
		// TODO Auto-generated method stub
		return producer;
	}

	@Override
	protected void sendMessage(Message m) throws JMSException {
		// TODO Auto-generated method stub
		producer.send(m);
	}

}
