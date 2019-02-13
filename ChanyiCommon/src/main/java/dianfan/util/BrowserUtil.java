/**  
* @Title: BrowserUtil.java
* @Package dianfan.util
* @Description: TODO
* @author Administrator
* @date 2018年7月17日 下午12:10:47
* @version V1.0  
*/
package dianfan.util;

/**
 * @Title: BrowserUtil.java
 * @Package dianfan.util
 * @Description: TODO
 * @author Administrator
 * @date 2018年7月17日 下午12:10:47
 * @version V1.0
 */
public class BrowserUtil {
	/**
	 * @Title: getBrowserName
	 * @Description: 获取浏览器名称
	 * @param agent
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午12:11:08
	 */
	public static String getBrowserName(String agent) {
		if (agent.indexOf("msie 7") > 0) {
			return "ie7";
		} else if (agent.indexOf("msie 8") > 0) {
			return "ie8";
		} else if (agent.indexOf("msie 9") > 0) {
			return "ie9";
		} else if (agent.indexOf("msie 10") > 0) {
			return "ie10";
		} else if (agent.indexOf("msie") > 0) {
			return "ie";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("firefox") > 0) {
			return "firefox";
		} else if (agent.indexOf("webkit") > 0) {
			return "webkit";
		} else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
			return "ie11";
		} else {
			return "Others";
		}
	}
}
