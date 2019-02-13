package dianfan.util;

import java.util.UUID;

/**
 * 
 * @Title: UUIDUtil.java
 * @Package dianfan.util
 * @Description: UUID生成工具类
 * @author Administrator
 * @date 2018年5月11日 上午11:36:49
 * @version V1.0
 */
public class UUIDUtil {
	/**
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	/**
	 * @param number
	 *            int
	 * @return String[] UUID
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
}
