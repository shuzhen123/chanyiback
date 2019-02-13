package dianfan.service;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

public class QueueMessageListener implements MessageListener {
    
    //当收到消息后，自动调用该方法
    @Override
    public void onMessage(Message message) {
    	System.out.println("-------------自动接收信息------------"+message);
    	// 如果是文本消息
        if (message instanceof TextMessage) {
            TextMessage tm = (TextMessage) message;
            try {
				System.out.println(" get textMessage：\t" + tm.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 

        // 如果是Map消息
        if (message instanceof MapMessage) {
            MapMessage mm = (MapMessage) message;
            try {
				System.out.println("get textMessage：type- " + mm.getString("type")+", sysOrderNo- "+mm.getString("sysOrderNo"));
				
            } catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        // 如果是Object消息
        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
			try {
				Serializable object = om.getObject();
				
				/*if(object instanceof WeixinOrderData){
					WeixinOrderData data = (WeixinOrderData) om.getObject();
					System.out.println(" get WeixinOrderData：\t" + data);
				}else if(object instanceof WeixinResult){
					WeixinResult data = (WeixinResult) om.getObject();
					System.out.println(" get WeixinResult：\t" + data);
				}*/
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }

        // 如果是bytes消息
        if (message instanceof BytesMessage) {
            byte[] b = new byte[1024];
            int len = -1;
            BytesMessage bm = (BytesMessage) message;
            try {
				while ((len = bm.readBytes(b)) != -1) {
				    System.out.println(new String(b, 0, len));
				}
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        // 如果是Stream消息
        if (message instanceof StreamMessage) {
            StreamMessage sm = (StreamMessage) message;
            try {
				System.out.println(sm.readString());
				System.out.println(sm.readInt());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    }

}