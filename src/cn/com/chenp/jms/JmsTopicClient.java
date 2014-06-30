package cn.com.chenp.jms;

import javax.jms.*;
//Ö÷Ìâ
public class JmsTopicClient extends JmsClient {
	private TopicConnection cn;
	private TopicSession session;
	private TopicPublisher producer;
	
	JmsTopicClient(TopicConnectionFactory factory,Topic dest) throws Exception{
		cn = factory.createTopicConnection();
		try {
			boolean transacted = false;
			int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;
			session = cn.createTopicSession(transacted, acknowledgeMode);
			producer = session.createPublisher(dest);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			close();
			throw e;
		}
		
	}
	

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
		producer.publish(m);
	}

}
