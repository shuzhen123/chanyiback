package dianfan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @Title: StringUtility.java
 * @Package dianfan.util
 * @Description: StringUtility工具类
 * @author Administrator
 * @date 2018年5月11日 下午2:19:39
 * @version V1.0
 */
public final class StringUtility {
	/**
	 * 字符串判断是否为空(不包含空字符串)
	 * 
	 * @param str
	 *            字符串
	 * @return true/false
	 */
	public static boolean isNull(String str) {
		if (str == null || str.trim().length() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串判断是否为数字
	 * 
	 * @param str
	 *            字符串
	 * @return true/false
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
