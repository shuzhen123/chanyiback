package dianfan.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Title: CompareBeans.java
 * @Package dianfan.util
 * @Description: 比较bean
 * @author Administrator
 * @date 2018年5月11日 上午11:29:07
 * @version V1.0
 */
public class CompareBeans {
	/**
	 * @Title: compare
	 * @Description: 列出两个对象之间相同不相同的值
	 * @param <T>
	 *            对象
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @return 返回各个属性比较结果一览
	 * @throws Exception
	 *             异常
	 * @time: 2018年5月11日 上午11:28:59
	 */
	public static <T> Map<String, String> compare(T obj1, T obj2) throws Exception {

		Map<String, String> result = new HashMap<String, String>();

		Field[] fs = obj1.getClass().getDeclaredFields();// 获取所有属性
		for (Field f : fs) {
			f.setAccessible(true);// 设置访问性，反射类的方法，设置为true就可以访问private修饰的东西，否则无法访问
			Object v1 = f.get(obj1);
			Object v2 = f.get(obj2);
			result.put(f.getName(), String.valueOf(equals(v1, v2)));
		}
		return result;
	}

	/**
	 * 两个对象间值得比较(false 相同,true 不相同)
	 * 
	 * @param <T>
	 *            对象
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * 
	 * @return 返回两个比较对象的结果
	 * @throws Exception
	 *             异常
	 */
	public static <T> boolean compare2(T obj1, T obj2) throws Exception {

		boolean result = false;

		Field[] fs = obj1.getClass().getDeclaredFields();// 获取所有属性
		for (Field f : fs) {
			f.setAccessible(true);// 设置访问性，反射类的方法，设置为true就可以访问private修饰的东西，否则无法访问
			Object v1 = f.get(obj1);
			Object v2 = f.get(obj2);
			if (!equals(v1, v2)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 比较两个对象
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @return 比较对象的结果返回
	 */
	private static boolean equals(Object obj1, Object obj2) {

		if (obj1 == obj2) {
			return true;
		}
		if (obj1 == null || obj2 == null) {
			return false;
		}
		return obj1.equals(obj2);
	}

}
