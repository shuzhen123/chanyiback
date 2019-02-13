/**  
* @Title: IpUtil.java
* @Package dianfan.util
* @Description: TODO
* @author Administrator
* @date 2018年7月3日 下午3:50:42
* @version V1.0  
*/
package dianfan.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: IpUtil.java
 * @Package dianfan.util
 * @Description: TODO
 * @author Administrator
 * @date 2018年7月3日 下午3:50:42
 * @version V1.0
 */
public class IpUtil {
	/**
	 * @Title: getIpAddr
	 * @Description: 获取真实IP地址
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午3:50:57
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
