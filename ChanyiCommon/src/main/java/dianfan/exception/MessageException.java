package dianfan.exception;

import java.net.UnknownHostException;

/**
 * 
 * @Title: MessageException.java
 * @Package dianfan.exception
 * @Description: 信息异常捕捉
 * @author Administrator
 * @date 2018年5月11日 下午2:40:49
 * @version V1.0
 */
public class MessageException extends Exception {

	/**
	 * 消息异常
	 * 
	 * @param string
	 *            内容
	 * @param e
	 *            异常
	 */
	public MessageException(String string, UnknownHostException e) {
	}

	/**
	 * 消息异常
	 * 
	 * @param string
	 *            内容
	 */
	public MessageException(String string) {
	}

	/**
	 * 消息异常
	 * 
	 * @param string
	 *            内容
	 * @param e
	 *            异常
	 */
	public MessageException(String string, Exception e) {
	}

}
