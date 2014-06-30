package cn.com.chenp.test;

import cn.com.chenp.jms.JmsClient;

public class JMSTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
/*		String topicName = readConfig("signal");
		String facName = readConfig("signal/@factory");*/
		String topicName ="";
		String facName = "";
		String msg = "";
		
		JmsClient.sendTo(facName, topicName, msg);
	}

}
