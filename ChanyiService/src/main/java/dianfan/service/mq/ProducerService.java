package dianfan.service.mq;

import java.util.Map;

/**
 * @ClassName ProducerService
 * @Description 消息生产服务层
 * @author cjy
 * @date 2018年6月28日 下午2:37:11
 */
public interface ProducerService {
	/**
	 * 向指定队列发送消息
	 */
	void sendMessage(String queryDestination, Object msg);

    /**
     * 向默认队列发送消息
     */
	void sendMessage(Object msg);
	
	/**
	 * 向默认队列发送消息
	 */
	void sendMapMessage(Map<String, String> msg);

}