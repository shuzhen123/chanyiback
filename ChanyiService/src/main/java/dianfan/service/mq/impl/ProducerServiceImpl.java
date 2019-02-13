package dianfan.service.mq.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import dianfan.service.mq.ProducerService;

/**
 * @ClassName ProducerServiceImpl
 * @Description 消息生产服务层实现
 * @author cjy
 * @date 2018年6月28日 下午2:37:55
 */
//@Service
public class ProducerServiceImpl implements ProducerService {
	@Autowired
    private JmsTemplate jmsTemplate;
       
	/**
	 * 向指定队列发送消息
	 */
	public void sendMessage(String queryDestination, final Object msg) {
        jmsTemplate.send(queryDestination, new MessageCreator() {
        	public Message createMessage(Session session) throws JMSException {
        		if (msg instanceof String){
        			return session.createTextMessage((String)msg);
        		}else{
        			return session.createObjectMessage((Serializable) msg);
        		}
        	}
        });
	}

    /**
     * 向默认队列发送消息
     */
	public void sendMessage(final Object msg) {
		
        jmsTemplate.send(new MessageCreator() {
        	public Message createMessage(Session session) throws JMSException {
        		if (msg instanceof String){
        			return session.createTextMessage((String)msg);
        		}else{
        			return session.createObjectMessage((Serializable) msg);
        		}
        	}
        });
	}
	
	/**
	 * 向默认队列发送Map消息
	 */
	public void sendMapMessage(final Map<String, String> msg) {
		
		jmsTemplate.send(new MessageCreator() {
			public MapMessage createMessage(Session session) throws JMSException {
				if(msg == null){
					return null;
				}
				MapMessage message = new ActiveMQMapMessage();
				
				for(Entry<String, String> m : msg.entrySet()){
					String key = m.getKey();
					String value = m.getValue();
					message.setString(key, value);
				}
				return message;
			}
		});
	}

}