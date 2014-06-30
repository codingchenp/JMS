package cn.com.chenp.jms;

import javax.jms.BytesMessage;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

public class JmsListener implements MessageListener, ExceptionListener {



	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		try {
			if (msg instanceof TextMessage) {
				process((TextMessage) msg);
			}
			else if (msg instanceof StreamMessage) {
				process((StreamMessage) msg);
			}
			else if (msg instanceof ObjectMessage) {
				process((ObjectMessage) msg);
			}
			else if (msg instanceof MapMessage) {
				process((MapMessage) msg);
			}
			else if (msg instanceof BytesMessage) {
				process((BytesMessage) msg);
			}
		}
		catch (Throwable e) {
			handleError(e);
		}
	}
	
	/**
	 * implements ExceptionListener
	 */
	public void onException(JMSException e)
	{
		handleError(e);
	}

	protected void handleError(Throwable e)
	{
	}

	protected void process(TextMessage text) throws Exception
	{
		String s = text.getText();
		process(s);
	}

	protected void process(String s) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	protected void process(StreamMessage stream) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	protected void process(ObjectMessage obj) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	protected void process(MapMessage map) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	protected void process(BytesMessage bytes) throws Exception
	{
		throw new UnsupportedOperationException();
	}
}
