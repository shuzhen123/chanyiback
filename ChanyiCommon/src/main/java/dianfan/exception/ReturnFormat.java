/**  
* @Title: ReturnFormat.java
* @Package dianfan.exception
* @Description: TODO
* @author Administrator
* @date 2018年5月31日 上午10:12:48
* @version V1.0  
*/
package dianfan.exception;

import java.util.Map;

import com.google.common.collect.Maps;

import dianfan.models.ResultBean;

/**
 * @Title: ReturnFormat.java
 * @Package dianfan.exception
 * @Description: 系统异常
 * @author Administrator
 * @date 2018年5月31日 上午10:12:48
 * @version V1.0
 */
// 格式化返回客户端数据格式（json）
public class ReturnFormat {
	private static Map<String, String> messageMap = Maps.newHashMap();
	// 初始化状态码与文字说明
	static {
		messageMap.put("400", "Bad Request!");
		messageMap.put("401", "NotAuthorization");
		messageMap.put("404", "Not Found");
		messageMap.put("405", "Method Not Allowed");
		messageMap.put("406", "Not Acceptable");
		messageMap.put("415", "Unsupported media type");
		messageMap.put("500", "Internal Server Error");

		messageMap.put("1000", "[服务器]运行时异常");
		messageMap.put("1001", "[服务器]空值异常");
		messageMap.put("1002", "[服务器]数据类型转换异常");
		messageMap.put("1003", "[服务器]IO异常");
		messageMap.put("1004", "[服务器]未知方法异常");
		messageMap.put("1005", "[服务器]数组越界异常");
		messageMap.put("1006", "[服务器]未知错误");

		messageMap.put("1007", "[服务器]由于除数为0引起的异常");
		messageMap.put("1008", "[服务器]由于数组存储空间不够引起的异常");
		messageMap.put("1009", "[服务器]文件没有找到系统异常");
		messageMap.put("1010", "[服务器]SQL执行异常");
		messageMap.put("1011", "[服务器]SQL语句异常");
		messageMap.put("1012", "[服务器]文件上传过大");
	}

	public static ResultBean retParam(String status, Object data) {
		return new ResultBean(status, messageMap.get(String.valueOf(status)), data);
	}
}
