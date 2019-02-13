package dianfan.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import dianfan.logger.Logger;

/**
 * 
 * @Title: PropertyUtil.java
 * @Package dianfan.util
 * @Description: property文件工具类
 * @author Administrator
 * @date 2018年5月11日 下午12:24:24
 * @version V1.0
 */
public final class PropertyUtil {
	/**
	 * 文件名称
	 */
	public static String PROPERTIES = "config-dev.properties";

	/**
	 * Properties文件
	 */
	private static Properties props = null;

	/**
	 * 初始静态代码块
	 */
	static {
		loadProps();
	}

	/**
	 * @Title: loadProps
	 * @Description: 加载properties
	 * @throws:
	 * @time: 2018年5月11日 下午1:33:40
	 */
	synchronized static private void loadProps() {
		props = new Properties();
		InputStream in = null;
		InputStreamReader isr = null;
		try {
			// <!--第一种，通过类加载器进行获取properties文件流-->
			in = PropertyUtil.class.getClassLoader().getResourceAsStream(PROPERTIES);
			// <!--第二种，通过类进行获取properties文件流-->
			// in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
			if (in != null) {
				isr = new InputStreamReader(in, "utf-8");
				props.load(isr);
			}
		} catch (FileNotFoundException e) {
			Logger.error("文件找不到！");
		} catch (Exception e) {
			Logger.error("出现Exception");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != isr) {
					isr.close();
				}
			} catch (IOException e) {
				in = null;
				isr = null;
				Logger.error(PROPERTIES + "文件流关闭出现异常");
			}
		}
	}

	/**
	 * @Title: getProperty
	 * @Description:获取键值
	 * @param key
	 *            key键
	 * @return 键值
	 * @throws:
	 * @time: 2018年5月11日 下午1:36:29
	 */
	public static String getProperty(String key) {
		if (props.isEmpty()) {
			loadProps();
		}
		if (StringUtility.isNull(props.getProperty(key))) {
			return null;
		}
		return props.getProperty(key).trim();
	}

	/**
	 * @Title: getProperty
	 * @Description: 获取键值(若值为null,默认设定值)
	 * @param key
	 *            key键
	 * @param defaultValue
	 *            默认值
	 * @return 键值
	 * @throws:
	 * @time: 2018年5月11日 下午1:34:34
	 */
	public static String getProperty(String key, String defaultValue) {
		if (props.isEmpty()) {
			loadProps();
		}
		if (StringUtility.isNull(props.getProperty(key, defaultValue))) {
			return null;
		}
		return props.getProperty(key, defaultValue).trim();
	}

}